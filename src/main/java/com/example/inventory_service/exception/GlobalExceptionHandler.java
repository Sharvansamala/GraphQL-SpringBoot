package com.example.inventory_service.exception;

import graphql.GraphQLError;
import org.springframework.graphql.data.method.annotation.GraphQlExceptionHandler;
import org.springframework.web.bind.annotation.ControllerAdvice;

@ControllerAdvice
public class GlobalExceptionHandler {

    @GraphQlExceptionHandler
    public GraphQLError handleProductNotFound(ProductNotFound e){
        return GraphQLError.newError()
                .message(e.getMessage())
                .build();
    }
}
