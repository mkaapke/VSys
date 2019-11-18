package IRC.Replies;

public class Reply {

    private Integer code;
    private String reply;
    private String message;

    public Reply(Integer code, String reply, String message) {
        this.code = code;
        this.reply = reply;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getReply() {
        return reply;
    }

    public void setReply(String error) {
        this.reply = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}