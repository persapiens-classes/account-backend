package org.persapiens.account.controller;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.persapiens.account.dto.ErrorDTO;

import org.springframework.http.MediaType;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Operation(summary = "Update")
@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Update ok"),
		@ApiResponse(responseCode = "400", description = "Invalid bean attributes on update",
				content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
						schema = @Schema(implementation = ErrorDTO.class))),
		@ApiResponse(responseCode = "404", description = "Bean attributes not found on update",
				content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
						schema = @Schema(implementation = ErrorDTO.class))),
		@ApiResponse(responseCode = "409", description = "Bean already exists on update",
				content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
						schema = @Schema(implementation = ErrorDTO.class))) })
public @interface UpdateBeanDoc {

}
