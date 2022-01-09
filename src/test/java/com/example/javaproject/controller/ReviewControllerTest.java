package com.example.javaproject.controller;

import com.example.javaproject.dto.ReviewDto;
import com.example.javaproject.mapper.ReviewMapper;
import com.example.javaproject.service.ReviewService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.junit.jupiter.api.Assertions.*;

@WebMvcTest(controllers = ReviewController.class)
class ReviewControllerTest {
    private static final Long ID = 1L;
    private static final long USER_ID = 1;
    private static final long PRODUCT_ID = 1;
    private static final String TEXT = "dummyText";

    @MockBean
    private ReviewService reviewService;

    @MockBean
    private ReviewMapper reviewMapper;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("Testing creating a product")
    void test_create() throws Exception {
        //Arrange
        ReviewDto dto = getReviewDto();
        when(reviewService.addReview(any())).thenReturn(dto);

        //Act
        MvcResult result = mockMvc.perform(post("/api/review/add/")
                .content(objectMapper.writeValueAsString(dto))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        //Assert
        assertThat(result.getResponse().getContentAsString()).isEqualTo(objectMapper.writeValueAsString(dto));
    }

    @Test
    @DisplayName("Testing updating a product")
    void test_update() throws Exception {
        //Arrange
        ReviewDto dto = getReviewDto();
        Long id = 1L;
        when(reviewService.updateReview(any(), any())).thenReturn(dto);

        //Act
        MvcResult result = mockMvc.perform(put("/api/review/update/" + id)
                .content(objectMapper.writeValueAsString(dto))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        //Assert
        assertThat(result.getResponse().getContentAsString()).isEqualTo(objectMapper.writeValueAsString(dto));
    }

    @Test
    @DisplayName("Testing delete a review")
    void test_delete() throws Exception {
        Long id = 1L;

        mockMvc.perform(delete("/api/review/delete/" + id))
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("Testing get a review")
    void test_getById() throws Exception {
        List<ReviewDto> dto = List.of(getReviewDto());
        Long id = 1L;
        when(reviewService.findReviewsByProduct(any())).thenReturn(dto);

        mockMvc.perform(get("/api/review/get/" + id))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].text", is(dto.get(0).getText())));
    }


    private ReviewDto getReviewDto() {
        return ReviewDto.builder()
                .text(TEXT)
                .id(ID)
                .productId(PRODUCT_ID)
                .userId(USER_ID)
                .build();
    }

}