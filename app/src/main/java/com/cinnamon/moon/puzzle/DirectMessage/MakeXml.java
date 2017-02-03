package com.cinnamon.moon.puzzle.DirectMessage;

import android.content.Context;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;
import twitter4j.DirectMessage;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class MakeXml {

    private Context context;

    public MakeXml(Context context) {
        this.context = context;
    }

    public void OutputXml(ArrayList<DirectMessage> masseages) {
        Document doc = new Document();
        Element root = new Element("directMessage");

        for (DirectMessage direct : masseages) {
            //root element
            Element message = new Element("message");
            //item element
            Element id = new Element("Id");
            message.addContent(id);
            id.setText(String.valueOf(direct.getId()));

            Element senderId = new Element("senderId");
            message.addContent(senderId);
            senderId.setText(String.valueOf(direct.getSenderId()));

            Element senderScN = new Element("senderScN");
            message.addContent(senderScN);
            senderScN.setText(direct.getSenderScreenName());

            Element raceId = new Element("raceId");
            message.addContent(raceId);
            raceId.setText(String.valueOf(direct.getRecipientId()));

            Element raceScN = new Element("raceScN");
            message.addContent(raceScN);
            raceScN.setText(direct.getRecipientScreenName());

            Element text = new Element("text");
            message.addContent(text);
            text.setText(direct.getText());

            Element time = new Element("time");
            message.addContent(time);
            time.setText(String.valueOf(direct.getCreatedAt()));

            root.addContent(message);
        }

        doc.setRootElement(root);

        try {
            File file = new File(context.getFilesDir(), "directMessage.xml");
            FileOutputStream stream = new FileOutputStream(file);
            XMLOutputter serializer = new XMLOutputter();

            Format f = serializer.getFormat();
            f.setEncoding("UTF-8");
            f.setIndent(" ");
            f.setLineSeparator("\r\n");
            f.setTextMode(Format.TextMode.TRIM);
            serializer.setFormat(f);

            serializer.output(doc, stream);
            stream.flush();
            stream.close();
        } catch (IOException e) {
            System.err.println(e);
        }

    }
}
