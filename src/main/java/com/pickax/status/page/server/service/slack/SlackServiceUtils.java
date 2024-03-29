package com.pickax.status.page.server.service.slack;

import com.slack.api.model.Attachment;
import com.slack.api.model.block.LayoutBlock;
import com.slack.api.model.block.composition.TextObject;

import java.util.ArrayList;
import java.util.List;

import static com.slack.api.model.block.Blocks.divider;
import static com.slack.api.model.block.Blocks.section;
import static com.slack.api.model.block.composition.BlockCompositions.markdownText;

public class SlackServiceUtils {
    private static final String ERROR_MESSAGE = "*Error Message:*\n";
    private static final String ERROR_STACK = "*Error Stack:*\n";
    private static final String FILTER_STRING = "pickax";

    public static List<Attachment> createAttachments(String color, List<LayoutBlock> data) {
        List<Attachment> attachments = new ArrayList<>();
        Attachment attachment = new Attachment();
        attachment.setColor(color);
        attachment.setBlocks(data);
        attachments.add(attachment);
        return attachments;
    }

    public static List<LayoutBlock> createErrorMessage(Exception exception, String message) {
        List<LayoutBlock> layoutBlockList = new ArrayList<>();

        List<TextObject> sectionInFields = new ArrayList<>();
        sectionInFields.add(markdownText(ERROR_MESSAGE + message));
        sectionInFields.add(markdownText(ERROR_STACK + exception));
        layoutBlockList.add(section(section -> section.fields(sectionInFields)));

        layoutBlockList.add(divider());
        layoutBlockList.add(section(section -> section.text(markdownText(filterErrorStack(exception.getStackTrace())))));
        return layoutBlockList;
    }

    private static String filterErrorStack(StackTraceElement[] stacks) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("```");
        for (StackTraceElement stack : stacks) {
            if (stack.toString().contains(FILTER_STRING)) {
                stringBuilder.append(stack).append("\n");
            }
        }
        stringBuilder.append("```");
        return stringBuilder.toString();
    }
}
