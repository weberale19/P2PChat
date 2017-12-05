package ch.ethz.inf.vs.a1.rubfisch.p2pchat;

/**
 * Created by ruben on 04.12.17.
 */

public class ChatMessage {

    private String text;
    private boolean owned;
    private String sender;
    private String time;

    public ChatMessage(String text, boolean owned, String sender, String time) {
        this.text = text;
        this.owned = owned;
        this.sender = sender;
        this.time = time;
    }


    public String getText() {
        return text;
    }

    public boolean isOwned() {
        return owned;
    }

    public String getSender() {
        return sender;
    }

    public String getTime() {return time;}
}
