package com.codegym.jira.profile;

import com.codegym.jira.common.to.BaseTo;
import com.codegym.jira.common.util.validation.Code;
import com.codegym.jira.common.util.validation.NoHtml;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(value = "id", allowSetters = true)
public class ContactTo extends BaseTo {
    @Code
    private String code;
    @NotBlank
    @Size(min = 2, max = 256)
    @NoHtml
    private String value;
}
