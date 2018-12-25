package bgu.spl.net.api;

public class ResponseContainer<T> {
    private T result;
    private boolean ack;
    private String error;
    Object additionalData;

    public boolean isAck() {
        return ack;
    }

    public void setAck(boolean ack) {
        this.ack = ack;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public Object getAdditionalData() {
        return additionalData;
    }

    public void setAdditionalData(Object additionalData) {
        this.additionalData = additionalData;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }
}
