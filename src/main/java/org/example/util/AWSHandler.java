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


public class AWSHandler implements Observer {

    // Configuration file path
    private final String CREDENTIALS_FILE_PATH = "src/main/resources/AWS/credentials.properties";
    private final String bucketName = "pirateprojectbucket"; // The bucket name

    private static AWSHandler instance = null;

    private AWSHandler() {
    }

    public static AWSHandler getInstance() {
        if (instance == null) {
            instance = new AWSHandler();
        }
        return instance;
    }

    //Update method
    @Override
    public void update() throws AWSException {

        if (AppState.getInstance().getGameState().getTitle().equals("New Game State")) {

            //Save a new instance
            saveAsNewGame();

        } else {
            String json = GameStateTranslator.gameStateToJson(AppState.getInstance().getGameState());

            //Overwrite an existed instance
            saveAnExistingGame(json, AppState.getInstance().getGameState().getTitle());
        }

    }

    // Save a new instance in S3
    private void saveAsNewGame() throws AWSException {

        Region region = Region.EU_NORTH_1;

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
        String keyName =  gameTitle + ".json";

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

    // Count the number of saved games in S3 without extension .json
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
                .region(Region.EU_NORTH_1)
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

    // Return an ArrayList with all contents of all saved games
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
                .region(Region.EU_NORTH_1)
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

    // Download the content of one specific saved game
    private String downloadFileContent(S3Client s3Client, String bucketName, String key) throws AWSException {
        try (InputStreamReader streamReader = new InputStreamReader(
                s3Client.getObject(GetObjectRequest.builder().bucket(bucketName).key(key).build()));
             BufferedReader reader = new BufferedReader(streamReader)) {

            return reader.lines().collect(Collectors.joining("\n"));

        } catch (IOException e) {
            throw new AWSException("Error occurred: Failed to communicate to AWS.");
        }
    }

    // Return all games title contained in S3
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
                .region(Region.EU_NORTH_1)
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

    //Return a specific json content
    public String getSavedGames(int gameID) throws AWSException {
        ArrayList<String> searchGame = loadFromS3();
        try {
            return searchGame.get(gameID - 1);
        } catch (Exception e) {
            throw new AWSException("Error occurred: Failed to communicate to AWS.");
        }
    }

    // Overwrite an existing game
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
                .region(Region.EU_NORTH_1)
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

    // Delete an existing instance: File name without extension
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
                .region(Region.EU_NORTH_1)
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


        }
    }
}