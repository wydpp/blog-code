package com.dpp.fsm;

/**
 * @author dpp
 * @date 2024/10/15
 * @Description
 */
public class ActionResult {
    private boolean success;
    private String message;

    public ActionResult(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("ActionResult{");
        sb.append("success=").append(success);
        sb.append(", message='").append(message).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
