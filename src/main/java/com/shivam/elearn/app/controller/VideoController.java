package com.shivam.elearn.app.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.shivam.elearn.app.dto.CoursesDto;
import com.shivam.elearn.app.dto.CustomMessage;
import com.shivam.elearn.app.dto.VideoDto;
import com.shivam.elearn.app.services.VideoService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/video")
public class VideoController {
	@Autowired
	private VideoService videoService;

	@PostMapping
	public ResponseEntity<VideoDto> create(@RequestBody VideoDto videoDto) {
		if (log.isInfoEnabled()) {
			log.info("create start");
		}
		VideoDto createVideo = videoService.createVideo(videoDto);
		if (log.isInfoEnabled()) {
			log.info("End create video");
		}
		return ResponseEntity.status(HttpStatus.CREATED).body(createVideo);
	}

	@PutMapping("/{videoId}")
	public ResponseEntity<VideoDto> update(@PathVariable String videoId, @RequestBody VideoDto videoDto) {

		VideoDto updateVideo = videoService.updateVideo(videoId, videoDto);
		return ResponseEntity.ok(updateVideo);
	}

	@GetMapping("/{videoId}")
	public ResponseEntity<VideoDto> getByVideoId(@PathVariable String videoId) {

		VideoDto byVideoId = videoService.getByVideoId(videoId);

		return ResponseEntity.ok(byVideoId);
	}

	@GetMapping
	public ResponseEntity<Page<VideoDto>> getAllVideo(Pageable pageable) {
		Page<VideoDto> allVideo = videoService.getAllVideo(pageable);
		return ResponseEntity.ok(allVideo);
	}

	@DeleteMapping("/{videoId}")
	public ResponseEntity<CustomMessage> deleteVideo(@PathVariable String videoId) {
		videoService.deleteVideo(videoId);
		CustomMessage customMessage = new CustomMessage();
		customMessage.setMessage("Delete is sucesfully");
		customMessage.setSucess(true);
		return ResponseEntity.ok(customMessage);
	}

	@GetMapping("/search")
	public ResponseEntity<List<VideoDto>> searchVideos(@RequestParam String keyword) {
		return ResponseEntity.ok(videoService.searchVideos(keyword));
	}

	@PostMapping("/{videoId}/video")
	public ResponseEntity<?> postVideo(@PathVariable String videoId, @RequestParam("video") MultipartFile video)
	
			throws IOException {
		if (log.isInfoEnabled()) {
			log.info("video upload start");
		}

		String contentType = video.getContentType();

		if (contentType == null) {
			contentType = "video/mp4";
		} else if (contentType.equalsIgnoreCase("video/mp4") || contentType.equalsIgnoreCase("video/mp4")) {
		} else {
			CustomMessage customMessage = new CustomMessage();
			customMessage.setSucess(false);
			customMessage.setMessage("Invalid file");

			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(customMessage);

		}

		if (log.isInfoEnabled()) {
			log.info("video upload end");
		}

		System.out.println(video.getOriginalFilename());
		System.out.println(video.getName());
		System.out.println(video.getSize());
		System.out.println(video.getContentType());
		VideoDto videoDto = videoService.saveVideo(video, videoId);
		return ResponseEntity.ok(videoDto);

	}

}
