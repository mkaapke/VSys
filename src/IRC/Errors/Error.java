package IRC.Errors;

public class Error {

    private Integer code;
    private String error;
    private String message;

    public Error(Integer code, String error, String message) {
        this.code = code;
        this.error = error;
        this.message = message;
    }


    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
