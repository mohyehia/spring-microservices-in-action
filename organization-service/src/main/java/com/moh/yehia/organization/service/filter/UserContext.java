package com.moh.yehia.organization.service.filter;

import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
public class UserContext {
    private String correlationId;
    private String userId;
}