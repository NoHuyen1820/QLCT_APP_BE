package com.qlct.core.dto.api;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.micronaut.http.HttpStatus;

import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseDTO<D> {

    /**
     * The header.
     */
    private Map<String, String> headers;

    /**
     * status
     */
    private String status = "200";// MessageConstants.STATUS_200;

    /**
     * data
     */
    D data;

    /**
     * {@inheritDoc}
     *
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "ResponseVO [status=" + status + ", data=" + data + "]";
    }

    public ResponseDTO(D data) {
        this.data = data;
    }

    /**
     *
     */
    public ResponseDTO() {
        // do nothing
    }

    /**
     * @return the status
     */
    public String getStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * Sets the status.
     *
     * @param status the new status
     */
    public void setStatus(int status) {
        this.status = String.valueOf(status);
    }

    /**
     * Sets the status.
     *
     * @param status the new status
     */
    public void setStatus(HttpStatus status) {
        this.status = String.valueOf(status.getCode());
    }

    /**
     * @return the data
     */
    public D getData() {
        return data;
    }

    /**
     * @param data the data to set
     */
    public void setData(D data) {
        this.data = data;
    }

    /**
     * Gets the headers.
     *
     * @return the headers
     */
    public Map<String, String> getHeaders() {
        return headers;
    }

    /**
     * Sets the headers.
     *
     * @param headers the headers
     */
    public void setHeaders(Map<String, String> headers) {
        this.headers = headers;
    }

}
