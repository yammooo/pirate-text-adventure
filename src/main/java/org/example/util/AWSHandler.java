package org.example.util;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Properties;
import java.util.UUID;
import java.util.ArrayList;
import java.util.stream.Collectors;

import org.example.exceptions.AWSException;
import org.example.json_translator.GameStateTranslator;
import org.example.model.AppState;
import org.example.observer.Observer;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;

/**
 * The AWSHandler class is responsible for handling interactions with AWS S3,
 * including saving, loading, and deleting game state data. It implements the Observer
 * interface to update game states.
 */
public class AWSHandler implements Observer {

    // Configuration file paths
    private final String CREDENTIALS_FILE_PATH = "src/main/resources/AWS/credentials.properties";
    private final String CONFIG_FILE_PATH = "src/main/resources/AWS/config.properties";

    private String bucketName; // The bucket name
    private Region region; // The AWS region

    private static AWSHandler instance = null;

    /**
     * Private constructor to enforce singleton pattern.
     * Initializes the configuration by loading properties from a file.
     */
    private AWSHandler() {
        loadConfig();
    }

    /**
     * Returns the singleton instance of AWSHandler.
     *
     * @return the singleton instance of AWSHandler.
     */
    public static AWSHandler getInstance() {
        if (instance == null) {
            instance = new AWSHandler();
        }
        return instance;
    }

    /**
     * Loads configuration properties from a file.
     */
    private void loadConfig() {
        Properties properties = new Properties();
        try (FileInputStream input = new FileInputStream(CONFIG_FILE_PATH)) {
            properties.load(input);
            this.bucketName = properties.getProperty("bucketName");
            this.region = Region.of(properties.getProperty("region"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Updates the game state in S3. Saves a new game state or updates an existing one.
     *
     * @throws AWSException if there is an error during the AWS S3 operation.
     */
    @Override
    public void update() throws AWSException {
        if (AppState.getInstance().getGameState().getTitle().equals("New Game State")) {
            saveAsNewGame();
        } else {
            String json = GameStateTranslator.gameStateToJson(AppState.getInstance().getGameState());
            saveAnExistingGame(json, AppState.getInstance().getGameState().getTitle());
        }
    }

    /**
     * Saves a new game state in S3 with a unique title.
     *
     * @throws AWSException if there is an error during the AWS S3 operation.
     */
    private void saveAsNewGame() throws AWSException {
        // Read credentials from the configuration file
        Properties properties = new Properties();
        try (FileInputStream input = new FileInputStream(CREDENTIALS_FILE_PATH)) {
            properties.load(input);
        } catch (IOException e) {
            throw new AWSException("Error occurred: Failed to communicate to AWS.");
        }

        String accessKey = properties.getProperty("accessKey");
        String secretKey = properties.getProperty("secretKey");

        // Create basic access credentials
        AwsBasicCredentials awsCreds = AwsBasicCredentials.create(accessKey, secretKey);

        // Configure the S3 client with basic access credentials
        S3Client s3Client = S3Client.builder()
                .region(region)
                .credentialsProvider(StaticCredentialsProvider.create(awsCreds))
                .build();

        // Generate a unique name for the JSON file
        String gameTitle = "game-" + UUID.randomUUID();
        String keyName = gameTitle + ".json";

        AppState.getInstance().getGameState().setTitle(gameTitle);
        String jsonContent = GameStateTranslator.gameStateToJson(AppState.getInstance().getGameState()); // The JSON content

        // Upload the JSON content to S3
        try (InputStream inputStream = new ByteArrayInputStream(jsonContent.getBytes(StandardCharsets.UTF_8))) {
            s3Client.putObject(PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key(keyName)
                    .build(), software.amazon.awssdk.core.sync.RequestBody.fromInputStream(inputStream, jsonContent.length()));

            System.out.println("JSON file successfully uploaded to S3 in " + bucketName + "/" + keyName);
        } catch (Exception e) {
            throw new AWSException("Error occurred: Failed to communicate to AWS.");
        }
    }

    /**
     * Counts the number of saved games in S3 with the .json extension.
     *
     * @return the number of saved games, or -1 if no JSON files are found.
     * @throws AWSException if there is an error during the AWS S3 operation.
     */
    public int countSavedGames() throws AWSException {
        int jsonFileCount = 0;

        // Read credentials from the configuration file
        Properties properties = new Properties();
        try (FileInputStream input = new FileInputStream(CREDENTIALS_FILE_PATH)) {
            properties.load(input);
        } catch (IOException e) {
            throw new AWSException("Error occurred: Failed to communicate to AWS.");
        }

        String accessKey = properties.getProperty("accessKey");
        String secretKey = properties.getProperty("secretKey");

        // Create access credentials
        AwsBasicCredentials awsCreds = AwsBasicCredentials.create(accessKey, secretKey);

        // Configure the S3 client with basic access credentials
        try (S3Client s3Client = S3Client.builder()
                .region(region)
                .credentialsProvider(StaticCredentialsProvider.create(awsCreds))
                .build()) {

            // ListObjectsV2Request is used to list the objects in the bucket
            ListObjectsV2Request listObjectsRequest = ListObjectsV2Request.builder()
                    .bucket(bucketName)
                    .build();

            ListObjectsV2Response listObjectsResponse;
            String continuationToken = null; // Used to get the next page of results

            // Check all the files in the bucket with .json extension
            do {
                if (continuationToken != null) {
                    listObjectsRequest = listObjectsRequest.toBuilder().continuationToken(continuationToken).build();
                }

                listObjectsResponse = s3Client.listObjectsV2(listObjectsRequest);
                for (S3Object object : listObjectsResponse.contents()) {
                    if (object.key().endsWith(".json")) {
                        jsonFileCount++;
                    }
                }

                continuationToken = listObjectsResponse.nextContinuationToken();
            } while (listObjectsResponse.isTruncated());

        } catch (Exception e) {
            throw new AWSException("Error occurred: Failed to communicate to AWS.");
        }

        return jsonFileCount == 0 ? -1 : jsonFileCount; // If no JSON file is found, return -1
    }

    /**
     * Loads all saved game contents from S3.
     *
     * @return an ArrayList containing the contents of all saved games.
     * @throws AWSException if there is an error during the AWS S3 operation.
     */
    public ArrayList<String> loadFromS3() throws AWSException {
        ArrayList<String> fileContents = new ArrayList<>();

        // Read credentials from the configuration file
        Properties properties = new Properties();
        try (FileInputStream input = new FileInputStream(CREDENTIALS_FILE_PATH)) {
            properties.load(input);
        } catch (IOException e) {
            throw new AWSException("Error occurred: Failed to communicate to AWS.");
        }

        String accessKey = properties.getProperty("accessKey");
        String secretKey = properties.getProperty("secretKey");

        // Create access credentials
        AwsBasicCredentials awsCreds = AwsBasicCredentials.create(accessKey, secretKey);

        // Configure the S3 client with basic access credentials
        try (S3Client s3Client = S3Client.builder()
                .region(region)
                .credentialsProvider(StaticCredentialsProvider.create(awsCreds))
                .build()) {

            ListObjectsV2Request listObjectsRequest = ListObjectsV2Request.builder()
                    .bucket(bucketName)
                    .build();

            ListObjectsV2Response listObjectsResponse;
            String continuationToken = null;

            do {
                if (continuationToken != null) {
                    listObjectsRequest = listObjectsRequest.toBuilder().continuationToken(continuationToken).build();
                }

                listObjectsResponse = s3Client.listObjectsV2(listObjectsRequest);
                for (S3Object object : listObjectsResponse.contents()) {
                    String content = downloadFileContent(s3Client, bucketName, object.key());
                    if (content != null) {
                        fileContents.add(content);
                    }
                }

                continuationToken = listObjectsResponse.nextContinuationToken();
            } while (listObjectsResponse.isTruncated());

        } catch (Exception e) {
            throw new AWSException("Error occurred: Failed to communicate to AWS.");
        }

        return fileContents;
    }

    /**
     * Downloads the content of a specific saved game from S3.
     *
     * @param s3Client the S3 client used for the operation.
     * @param bucketName the name of the S3 bucket.
     * @param key the key of the object to download.
     * @return the content of the file as a String.
     * @throws AWSException if there is an error during the AWS S3 operation.
     */
    private String downloadFileContent(S3Client s3Client, String bucketName, String key) throws AWSException {
        try (InputStreamReader streamReader = new InputStreamReader(
                s3Client.getObject(GetObjectRequest.builder().bucket(bucketName).key(key).build()));
             BufferedReader reader = new BufferedReader(streamReader)) {

            return reader.lines().collect(Collectors.joining("\n"));

        } catch (IOException e) {
            throw new AWSException("Error occurred: Failed to communicate to AWS.");
        }
    }

    /**
     * Returns the titles of all saved games contained in S3.
     *
     * @return an ArrayList of game titles.
     * @throws AWSException if there is an error during the AWS S3 operation.
     */
    public ArrayList<String> getGamesTitles() throws AWSException {
        ArrayList<String> fileNames = new ArrayList<>();

        // Read credentials from the configuration file
        Properties properties = new Properties();
        try (FileInputStream input = new FileInputStream(CREDENTIALS_FILE_PATH)) {
            properties.load(input);
        } catch (IOException e) {
            throw new AWSException("Error occurred: Failed to communicate to AWS.");
        }

        String accessKey = properties.getProperty("accessKey");
        String secretKey = properties.getProperty("secretKey");

        // Create access credentials
        AwsBasicCredentials awsCreds = AwsBasicCredentials.create(accessKey, secretKey);

        // Configure the S3 client with basic access credentials
        try (S3Client s3Client = S3Client.builder()
                .region(region)
                .credentialsProvider(StaticCredentialsProvider.create(awsCreds))
                .build()) {

            ListObjectsV2Request listObjectsRequest = ListObjectsV2Request.builder()
                    .bucket(bucketName)
                    .build();

            ListObjectsV2Response listObjectsResponse;
            String continuationToken = null;

            do {
                if (continuationToken != null) {
                    listObjectsRequest = listObjectsRequest.toBuilder().continuationToken(continuationToken).build();
                }

                listObjectsResponse = s3Client.listObjectsV2(listObjectsRequest);
                for (S3Object object : listObjectsResponse.contents()) {
                    String key = object.key();
                    String fileNameWithoutExtension = key.contains(".") ? key.substring(0, key.lastIndexOf('.')) : key;
                    fileNames.add(fileNameWithoutExtension);
                }

                continuationToken = listObjectsResponse.nextContinuationToken();
            } while (listObjectsResponse.isTruncated());

        } catch (Exception e) {
            throw new AWSException("Error occurred: Failed to communicate to AWS.");
        }

        return fileNames;
    }

    /**
     * Returns the content of a specific saved game by its ID.
     *
     * @param gameID the ID of the game to retrieve.
     * @return the content of the saved game as a String.
     * @throws AWSException if there is an error during the AWS S3 operation.
     */
    public String getSavedGames(int gameID) throws AWSException {
        ArrayList<String> searchGame = loadFromS3();
        try {
            return searchGame.get(gameID - 1);
        } catch (Exception e) {
            throw new AWSException("Error occurred: Failed to communicate to AWS.");
        }
    }

    /**
     * Overwrites an existing game in S3 with new content.
     *
     * @param json the new content of the game in JSON format.
     * @param fileName the name of the file to overwrite.
     * @throws AWSException if there is an error during the AWS S3 operation.
     */
    public void saveAnExistingGame(String json, String fileName) throws AWSException {

        // Read credentials from the configuration file
        Properties properties = new Properties();
        try (FileInputStream input = new FileInputStream(CREDENTIALS_FILE_PATH)) {
            properties.load(input);
        } catch (IOException e) {
            throw new AWSException("Error occurred: Failed to communicate to AWS.");
        }

        String accessKey = properties.getProperty("accessKey");
        String secretKey = properties.getProperty("secretKey");

        // Create access credentials
        AwsBasicCredentials awsCreds = AwsBasicCredentials.create(accessKey, secretKey);

        // Configure the S3 client with basic access credentials
        try (S3Client s3Client = S3Client.builder()
                .region(region)
                .credentialsProvider(StaticCredentialsProvider.create(awsCreds))
                .build()) {

            ListObjectsV2Request listObjectsRequest = ListObjectsV2Request.builder()
                    .bucket(bucketName)
                    .build();

            ListObjectsV2Response listObjectsResponse;
            String continuationToken = null;
            boolean fileFound = false;

            do {
                if (continuationToken != null) {
                    listObjectsRequest = listObjectsRequest.toBuilder().continuationToken(continuationToken).build();
                }

                listObjectsResponse = s3Client.listObjectsV2(listObjectsRequest);
                for (S3Object object : listObjectsResponse.contents()) {
                    String key = object.key();
                    String keyWithoutExtension = key.contains(".") ? key.substring(0, key.lastIndexOf('.')) : key;
                    if (keyWithoutExtension.equals(fileName)) {
                        // If the file is found, overwrite the content
                        s3Client.putObject(PutObjectRequest.builder()
                                .bucket(bucketName)
                                .key(key)
                                .build(), RequestBody.fromString(json, StandardCharsets.UTF_8));
                        fileFound = true;
                        break;
                    }
                }

                continuationToken = listObjectsResponse.nextContinuationToken();
            } while (listObjectsResponse.isTruncated() && !fileFound);

            if (!fileFound) {
                throw new AWSException("Error occurred: Failed to find the file in the bucket.");
            }

        } catch (Exception e) {
            throw new AWSException("Error occurred: Failed to communicate to AWS.");
        }
    }

    /**
     * Deletes an existing game from S3.
     *
     * @param fileName the name of the file to delete (without extension).
     * @throws AWSException if there is an error during the AWS S3 operation.
     */
    public void deleteGame(String fileName) throws AWSException {

        // Read credentials from the configuration file
        Properties properties = new Properties();
        try (FileInputStream input = new FileInputStream(CREDENTIALS_FILE_PATH)) {
            properties.load(input);
        } catch (IOException e) {
            throw new AWSException("Error occurred: Failed to communicate to AWS.");
        }

        String accessKey = properties.getProperty("accessKey");
        String secretKey = properties.getProperty("secretKey");

        // Create access credentials
        AwsBasicCredentials awsCreds = AwsBasicCredentials.create(accessKey, secretKey);

        // Configure the S3 client with basic access credentials
        try (S3Client s3Client = S3Client.builder()
                .region(region)
                .credentialsProvider(StaticCredentialsProvider.create(awsCreds))
                .build()) {

            ListObjectsV2Request listObjectsRequest = ListObjectsV2Request.builder()
                    .bucket(bucketName)
                    .build();

            ListObjectsV2Response listObjectsResponse;
            String continuationToken = null;
            boolean fileFound = false;

            do {
                if (continuationToken != null) {
                    listObjectsRequest = listObjectsRequest.toBuilder().continuationToken(continuationToken).build();
                }

                listObjectsResponse = s3Client.listObjectsV2(listObjectsRequest);
                for (S3Object object : listObjectsResponse.contents()) {
                    String key = object.key();
                    String keyWithoutExtension = key.contains(".") ? key.substring(0, key.lastIndexOf('.')) : key;
                    if (keyWithoutExtension.equals(fileName)) {
                        // If the file is found, delete it
                        s3Client.deleteObject(DeleteObjectRequest.builder()
                                .bucket(bucketName)
                                .key(key)
                                .build());
                        fileFound = true;
                        break;
                    }
                }

                continuationToken = listObjectsResponse.nextContinuationToken();
            } while (listObjectsResponse.isTruncated() && !fileFound);

            if (!fileFound) {
                throw new AWSException("Error occurred: Failed to communicate to AWS.");
            }

        } catch (Exception e) {
            throw new AWSException("Error occurred: Failed to communicate to AWS.");
        }
    }
}