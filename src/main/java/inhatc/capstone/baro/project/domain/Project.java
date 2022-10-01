package inhatc.capstone.baro.project.domain;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import inhatc.capstone.baro.member.domain.Member;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@AllArgsConstructor
public class Project {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String title;
	private Long viewCount;
	private Long likeCount;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "leader_id")
	private Member leader;

	@OneToOne(mappedBy = "project", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private ProjectDetail detail;

	public void setDetail(ProjectDetail detail) {
		this.detail = detail;
	}
}
