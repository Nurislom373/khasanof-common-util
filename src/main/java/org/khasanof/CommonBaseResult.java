package org.khasanof;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Objects;

/**
 * @author Nurislom
 * @see org.khasanof
 * @since 12/2/2023 12:41 AM
 */
@Getter
@Setter
@ToString
public class CommonBaseResult implements BaseResult {

    private boolean success;
    private Integer code;
    private String message;

    public CommonBaseResult(boolean success, Integer code, String message) {
        this.success = success;
        this.code = code;
        this.message = message;
    }

    public CommonBaseResult success(boolean success) {
        this.success = success;
        return this;
    }

    public CommonBaseResult code(Integer code) {
        this.code = code;
        return this;
    }

    public CommonBaseResult message(String message) {
        this.message = message;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CommonBaseResult that = (CommonBaseResult) o;

        if (success != that.success) return false;
        if (!Objects.equals(code, that.code)) return false;
        return Objects.equals(message, that.message);
    }

    @Override
    public int hashCode() {
        int result = (success ? 1 : 0);
        result = 31 * result + (code != null ? code.hashCode() : 0);
        result = 31 * result + (message != null ? message.hashCode() : 0);
        return result;
    }

}
