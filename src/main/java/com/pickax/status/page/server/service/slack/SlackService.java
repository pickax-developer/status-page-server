package com.pickax.status.page.server.service.slack;

import com.slack.api.Slack;
import com.slack.api.methods.SlackApiException;
import com.slack.api.model.Attachment;
import com.slack.api.model.block.LayoutBlock;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Slf4j
@Service
public class SlackService {
    @Value(value = "${profiles.name}")
    String profile;

    @Value(value = "${slack.token}")
    String token;

    @Value(value = "${slack.channel.monitor}")
    String channel;

    private static final String PROD = "prod";
    private static final String PROD_ERROR_MESSAGE_TITLE = "🐤 *꽤액!!! 꽤애액!!! 에러 발생!!!*";
    private static final String CLIENT_5xx_ERROR_COLOR = "#e82415";
    private static final String CLIENT_4xx_ERROR_COLOR = "#e8bf1a";

    public void sendErrorMessage(Exception exception, String message, HttpStatus httpStatus) {
        if (PROD.equals(profile)) {
            try {
                String color = httpStatus.is4xxClientError() ? CLIENT_4xx_ERROR_COLOR : CLIENT_5xx_ERROR_COLOR;
                List<LayoutBlock> layoutBlocks = SlackServiceUtils.createErrorMessage(exception, message);
                List<Attachment> attachments = SlackServiceUtils.createAttachments(color, layoutBlocks);
                Slack.getInstance().methods(token).chatPostMessage(request ->
                        request.channel(channel)
                                .attachments(attachments)
                                .text(PROD_ERROR_MESSAGE_TITLE));
            } catch (SlackApiException | IOException e) {
                log.error(e.getMessage(), e);
            }
        }
    }
}
