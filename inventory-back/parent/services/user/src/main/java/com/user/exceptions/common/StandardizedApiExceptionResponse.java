package com.user.exceptions.common;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Standardized error response based on RFC 7807")
public class StandardizedApiExceptionResponse {

    @Schema(example = "/error/authentication/not-authorized", requiredMode = Schema.RequiredMode.REQUIRED)
    private String type;

    @Schema(example = "The user doesn't have authorization to access this resource", requiredMode = Schema.RequiredMode.REQUIRED)
    private String title;

    @Schema(example = "1001", requiredMode = Schema.RequiredMode.REQUIRED)
    private String code;

    @Schema(example = "This resource is private, please contact admin", requiredMode = Schema.RequiredMode.REQUIRED)
    private String detail;

    @Schema(example = "/errors/authentication/not-authorized/01", requiredMode = Schema.RequiredMode.REQUIRED)
    private String instance;

    public StandardizedApiExceptionResponse(String code, String detail, String instance, String title, String type) {
        this.code = code;
        this.detail = detail;
        this.instance = instance;
        this.title = title;
        this.type = type;
    }

    public StandardizedApiExceptionResponse() {
    }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }

    public String getDetail() { return detail; }
    public void setDetail(String detail) { this.detail = detail; }

    public String getInstance() { return instance; }
    public void setInstance(String instance) { this.instance = instance; }

}