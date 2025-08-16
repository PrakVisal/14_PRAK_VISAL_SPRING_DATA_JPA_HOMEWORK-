package com.example.springdata_homework.utils;

import com.example.springdata_homework.model.dto.response.Pagination;
import com.example.springdata_homework.model.dto.response.Response;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;


public class RequestUtils {

    public static <T> Response<T> getResponse(HttpStatusCode code, String message, T data) {
        return new Response<>(
                code,
                message,
                OffsetDateTime.now(),
                data,
                null
        );
    }


    public static <T> Response<T> getPaginatedResponse(
            HttpStatusCode code,
            String message,
            T data,
            int page,
            int limit,
            long totalCount) {

        int totalPages = (int) Math.ceil((double) totalCount / limit);

        Pagination pagination = Pagination.builder()
                .page(page)
                .limit(limit)
                .totalCount((int) totalCount)
                .totalPages(totalPages)
                .nextPage(page < totalPages ? page + 1 : totalPages)
                .previousPage(page > 1 ? page - 1 : 1)
                .offset((page - 1) * limit)
                .pagesToShow(totalPages)
                .startPage(Math.max(1, page - 2))
                .endPage(Math.min(totalPages, page + 2))
                .build();

        return new Response<>(
                code,
                message,
                OffsetDateTime.now(),
                data,
                pagination
        );
    }

    public static ProblemDetail handleDetailsException(HttpServletRequest request, String message, HttpStatus status) {
        ProblemDetail detail = ProblemDetail.forStatus(status);
        detail.setProperty("code", "Error");
        detail.setProperty("errors", message);
        detail.setProperty("timestamp", LocalDateTime.now());
//        detail.setType(URI.create(API_URL + request.getRequestURI()));
        return detail;
    }
}
