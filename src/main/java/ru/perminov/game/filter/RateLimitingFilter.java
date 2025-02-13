package ru.perminov.game.filter;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Component
public class RateLimitingFilter implements Filter {
    private final ConcurrentMap<String, RequestInfo> userRequests = new ConcurrentHashMap<>();

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String userId = getUserIdFromRequest(httpRequest); // Метод для получения userId из запроса

        RequestInfo requestInfo = userRequests.computeIfAbsent(userId, k -> new RequestInfo());

        if (requestInfo.canAllowRequest()) {
            requestInfo.incrementRequestCount();
            chain.doFilter(request, response);
        } else {
            response.getOutputStream().println("Rate limit exceeded. Please try again later.");
        }
    }

    private String getUserIdFromRequest(HttpServletRequest request) {
        // Пример: получение userId из заголовка запроса
        return request.getHeader("userId");
    }

    private class RequestInfo {
        private int requestCount = 0;
        private LocalDateTime lastResetTime = LocalDateTime.now();

        boolean canAllowRequest() {
            if (LocalDateTime.now().isAfter(lastResetTime.plusMinutes(1))) {
                resetRequestCount();
            }
            return requestCount < 10; // Ограничение в 10 запросов в минуту
        }

        void incrementRequestCount() {
            requestCount++;
        }

        void resetRequestCount() {
            requestCount = 0;
            lastResetTime = LocalDateTime.now();
        }
    }
}
