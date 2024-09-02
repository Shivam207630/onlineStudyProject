package com.shivam.elearn.app.services;

import java.io.IOException;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import com.shivam.elearn.app.dto.VideoDto;

public interface VideoService {

	VideoDto createVideo(VideoDto videoDto);

	VideoDto updateVideo(String videoId, VideoDto videoDto);

	VideoDto getByVideoId(String videoId);

	Page<VideoDto> getAllVideo(Pageable pageable);

	void deleteVideo(String videoId);

	List<VideoDto> searchVideos(String keyword);

	public VideoDto saveVideo(MultipartFile file, String videoId) throws IOException;

}
