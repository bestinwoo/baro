package inhatc.capstone.baro.project.domain;

import java.time.LocalDate;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;

import inhatc.capstone.baro.lounge.Lounge;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Builder
@AllArgsConstructor
public class ProjectDetail {
	@Id
	private Long id;
	private String content;
	private LocalDate startDate;
	private LocalDate endDate;
	private String purpose; // 프로젝트 목적

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "lounge_id")
	private Lounge lounge; // 라운지에서 아이디 참고한 경우

	@OneToOne
	@MapsId
	@JoinColumn(name = "id")
	private Project project;

	public void setProject(Project project) {
		this.project = project;
	}
}