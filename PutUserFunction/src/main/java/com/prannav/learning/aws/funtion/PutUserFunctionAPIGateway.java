package com.prannav.learning.aws.funtion;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.prannav.learning.aws.funtion.model.UserData;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.PutItemRequest;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PutUserFunctionAPIGateway implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    private static final Map<String, String> HEADERS = new HashMap<>();

    static {

        HEADERS.put("Content-Type", "application/json");
        HEADERS.put("X-Custom-Header", "application/json");
    }
    private static final String TABLE_NAME = System.getenv("TABLE_NAME");
    private static final Region REGION = Region.of(System.getenv("REGION_NAME"));
    private final DynamoDbClient dynamoDbClient = DynamoDbClient.builder().region(REGION).build();

    @Override
    public APIGatewayProxyResponseEvent handleRequest(APIGatewayProxyRequestEvent input, Context context) {
        final LambdaLogger logger = context.getLogger();
        logger.log("Received request for " + input.getResource());
        try {
            final UserData userData = OBJECT_MAPPER.readValue(input.getBody(), UserData.class);
            logger.log("User Data: " + userData);

            final String userId = UUID.randomUUID()
                                      .toString();
            userData.setUserId(userId);
            logger.log("Saving User Data: " + userData);
            final DynamoDbEnhancedClient enhancedClient = DynamoDbEnhancedClient.builder().dynamoDbClient(dynamoDbClient).build();
            final DynamoDbTable<UserData> userTable = enhancedClient.table(TABLE_NAME, TableSchema.fromBean(UserData.class));
            userTable.putItem(userData);


            return new APIGatewayProxyResponseEvent()
                    .withHeaders(HEADERS)
                    .withStatusCode(200)
                    .withBody("Created user. Id: " + userId);

        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
