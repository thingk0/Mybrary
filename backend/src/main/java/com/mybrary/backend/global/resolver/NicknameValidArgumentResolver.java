package com.mybrary.backend.global.resolver;

import com.mybrary.backend.global.annotation.Nickname;
import com.mybrary.backend.global.exception.member.InvalidNicknameException;
import com.mybrary.backend.global.exception.member.MissingPathVariableException;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Pattern;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.servlet.HandlerMapping;

@Log4j2
@Component
public class NicknameValidArgumentResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterAnnotation(Nickname.class) != null &&
            String.class.equals(parameter.getParameterType());
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {

        String nickname = getNicknameFromUriTemplateVars(webRequest);
        log.info("Attempting to resolve nickname: {}", nickname);

        if (!validateNickname(nickname)) {
            log.error("Invalid nickname format: {}", nickname);
            throw new InvalidNicknameException();
        }

        log.info("Successfully validated nickname: {}", nickname);
        return nickname;
    }

    private String getNicknameFromUriTemplateVars(NativeWebRequest webRequest) {
        Map<String, String> uriTemplateVars = (Map<String, String>) webRequest.getAttribute(
            HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE,
            RequestAttributes.SCOPE_REQUEST);

        return Optional.ofNullable(uriTemplateVars)
                       .map(vars -> vars.get("nickname"))
                       .orElseThrow(MissingPathVariableException::new);
    }

    private boolean validateNickname(String nickname) {
        boolean isValid = Pattern.matches("^[a-zA-Z0-9_]{3,15}$", nickname);
        log.debug("Validating nickname '{}': {}", nickname, isValid);
        return isValid;
    }


}

