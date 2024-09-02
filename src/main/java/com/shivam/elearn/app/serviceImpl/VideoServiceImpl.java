package com.shivam.elearn.app.serviceImpl;

import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.shivam.elearn.app.config.AppConstant;
import com.shivam.elearn.app.dto.VideoDto;
import com.shivam.elearn.app.entity.Video;
import com.shivam.elearn.app.repository.VideoRepo;
import com.shivam.elearn.app.services.FileService;
import com.shivam.elearn.app.services.VideoService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class VideoServiceImpl implements VideoService{
	@Autowired
	private VideoRepo videoRepo;
	@Autowired
	private ModelMapper modelMapper;
	@Autowired
	private FileService fileService;

	@Override
	public VideoDto createVideo(VideoDto videoDto) {
		if (log.isInfoEnabled()) {
			log.info("video create Start ");
		}
		videoDto.setVideoId(UUID.randomUUID().toString());
		Video video = modelMapper.map(videoDto, Video.class);
		Video save = videoRepo.save(video);
		VideoDto savedvideoDto = modelMapper.map(save, VideoDto.class);
		
		if (log.isInfoEnabled()) {
			log.info("video create End ");
		}
		return savedvideoDto;
	}

	@Override
	public VideoDto updateVideo(String videoId, VideoDto videoDto) {
		if (log.isInfoEnabled()) {
			log.info("video update Start ");
		}
		Video video = videoRepo.findById(videoId).orElseThrow(() -> new RuntimeException("Video not found !"));
		modelMapper.map(video, VideoDto.class);
		
		video.setTitle(videoDto.getTitle());
		video.setDesc(videoDto.getDesc());
		video.setFilePath(videoDto.getFilePath());
		video.setContentType(videoDto.getContentType());
		
		Video save = videoRepo.save(video);
		VideoDto updateVideoDto = modelMapper.map(save, VideoDto.class);
		
		if (log.isInfoEnabled()) {
			log.info("video update End ");
		}
		return updateVideoDto;
	}

	@Override
	public VideoDto getByVideoId(String videoId) {
		if (log.isInfoEnabled()) {
			log.info("video findById Start ");
		}
		Video video = videoRepo.findById(videoId).orElseThrow(() -> new RuntimeException("Video not found !"));
        VideoDto videoDto = modelMapper.map(video, VideoDto.class);
        if (log.isInfoEnabled()) {
			log.info("video findById End ");
		}
		return videoDto;
	}

	@Override
	public Page<VideoDto> getAllVideo(Pageable pageable) {
		if (log.isInfoEnabled()) {
			log.info("video findAll Start ");
		}
		
		Page<Video> findAll = videoRepo.findAll(pageable);
		List<VideoDto> collect = findAll.getContent().stream()
				.map(video -> modelMapper.map(video, VideoDto.class))
				.collect(Collectors.toList());
		
		if (log.isInfoEnabled()) {
			log.info("video findAll End ");
		}
		return new PageImpl<>(collect,pageable,findAll.getTotalElements());
	}

	@Override
	public void deleteVideo(String videoId) {
		if (log.isInfoEnabled()) {
			log.info("video delete Start ");
		}
		videoRepo.deleteById(videoId);
		
		if (log.isInfoEnabled()) {
			log.info("video delete End ");
		}
		
	}

	@Override
	public List<VideoDto> searchVideos(String keyword) {
		List<Video> videos = videoRepo.findByTitleContainingIgnoreCaseOrDescContainingIgnoreCase(keyword, keyword);
        return videos.stream()
                .map(video -> modelMapper.map(video, VideoDto.class))
                .collect(Collectors.toList());
	}

	@Override
	public VideoDto saveVideo(MultipartFile file, String videoId) throws IOException {
		Video video = videoRepo.findById(videoId).orElseThrow(() -> new RuntimeException("Video not found !"));
        String save = fileService.save(file, AppConstant.COURSE_VIDEO_DIR, file.getOriginalFilename());
        video.setFilePath(save);
        video.setContentType(file.getContentType());
		return modelMapper.map(videoRepo.save(video), VideoDto.class);
	}

}
