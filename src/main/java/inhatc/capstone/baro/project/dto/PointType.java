package inhatc.capstone.baro.project.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PointType {
	CREATE_PROJECT(100),
	JOIN_PROJECT(50),
	COMPLETE_PROJECT(100),
	SHARED_IDEA(50),
	WRITE_IDEA(30);

	private final int point;
}
