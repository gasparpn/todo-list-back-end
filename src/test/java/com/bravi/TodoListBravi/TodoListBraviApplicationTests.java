package com.bravi.TodoListBravi;

import com.bravi.TodoListBravi.constants.Status;
import com.bravi.TodoListBravi.model.TodoItem;
import com.bravi.TodoListBravi.repository.TodoRepository;

import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import javax.annotation.Resource;
import java.time.LocalDateTime;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
class TodoListBraviApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	@Resource
	private TodoRepository todoRepository;

	@Test
	public void testGetReturnEmptyListWhenThereIsNoTodoItem() throws Exception {
		mockMvc
				.perform(MockMvcRequestBuilders.get( "/todo"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().string("[]"));
	}

	@Test
	public void testPostThrowBadRequestWhenScheduledForIsLessThanScheduledAt() throws Exception {
		TodoItem todoItem = new TodoItem();
		todoItem.setTitle("Test Task");
		todoItem.setScheduledAt(LocalDateTime.of(2018, 1, 25, 10,10, 10));
		todoItem.setScheduledFor(LocalDateTime.of(2018, 1, 24, 10,10, 10));
		mockMvc
				.perform(MockMvcRequestBuilders.post( "/todo")
				.content(todoItem.toString())
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isBadRequest());
	}

	@Test
	public void testPutThrowBadRequestWhenScheduledForIsLessThanScheduledAt() throws Exception {
		TodoItem todoItem = new TodoItem();
		todoItem.setTitle("Test Task");
		todoItem.setScheduledAt(LocalDateTime.of(2018, 1, 25, 10,10, 10));
		todoItem.setScheduledFor(LocalDateTime.of(2018, 1, 24, 10,10, 10));
		mockMvc
				.perform(MockMvcRequestBuilders.put( "/todo")
						.content(todoItem.toString())
						.contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isBadRequest());
	}

	@Test
	public void testPostThrowBadRequestWhenThereIsNoTitle() throws Exception {
		TodoItem todoItem = new TodoItem();
		todoItem.setStatus(Status.TODO);
		todoItem.setScheduledAt(LocalDateTime.of(2018, 1, 25, 10,10, 10));
		todoItem.setScheduledFor(LocalDateTime.of(2018, 1, 26, 10,10, 10));
		mockMvc
				.perform(MockMvcRequestBuilders.post( "/todo")
						.content(todoItem.toString())
						.contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isBadRequest());
	}

	@Test
	public void testPutRescheduleTask() throws Exception {
		// Tasks can be scheduled to be done on another date
		TodoItem todoItem = new TodoItem();
		todoItem.setTitle("Title Task");
		todoItem.setScheduledAt(LocalDateTime.of(2020, 12, 27, 10,10, 10));
		todoItem.setScheduledFor(LocalDateTime.of(2020, 12, 28, 10,10, 10));
		todoRepository.save(todoItem);
		JSONObject newItem = new JSONObject();
		newItem.put("id", todoItem.getId());
		newItem.put("title", "TodoItem test");
		newItem.put("status", "DOING");
		newItem.put("scheduledAt", "2020-12-27T10:10:10");
		newItem.put("scheduledFor", "2020-12-29T21:34:55");
		mockMvc
				.perform(MockMvcRequestBuilders.put( "/todo")
						.content(newItem.toString())
						.contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().json(newItem.toString()));
	}

	@Test
	public void testDeleteThrowBadRequestWhenTaskDontExists() throws Exception {
		mockMvc
				.perform(MockMvcRequestBuilders.delete( "/todo/9999"))
				.andExpect(MockMvcResultMatchers.status().isNotFound());
	}

	@Test
	public void testDeleteThrowBadRequestWhenTaskIsNotFinished() throws Exception {
		// Tasks can be edited or deleted if they are not completed
		TodoItem todoItem = new TodoItem();
		todoItem.setTitle("Title Task");
		todoItem.setTitle("DOING");
		todoItem.setScheduledAt(LocalDateTime.of(2020, 12, 27, 10,10, 10));
		todoItem.setScheduledFor(LocalDateTime.of(2020, 12, 28, 10,10, 10));
		todoRepository.save(todoItem);
		mockMvc
				.perform(MockMvcRequestBuilders.delete( "/todo/".concat(todoItem.getId().toString())))
				.andExpect(MockMvcResultMatchers.status().isBadRequest());
	}
}
