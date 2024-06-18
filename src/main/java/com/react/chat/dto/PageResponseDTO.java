package com.react.chat.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Data
public class PageResponseDTO<E> {
  private List<E> list;   // 글 목록(데이터)
  private List<Integer> pageNumList;  // 화면에 뿌려줄 페이지 번호들
  private PageRequestDTO pageRequestDTO; // 요청정보 : page, size
  private boolean prev, next; // 다음과 이전 페이지 있는지 여부
  private long totalCount;    // 전체 글 개수
  private int prevPage, nextPage; // prev눌렀을때 이동할 페이지번호, next눌렀을때 이동할 페이지번호
  private int pageNumLength; // 현재 보여주는 페이지 번호의 개수
  private int current; // 현재페이지
  private final int PAGE_NUM_UNIT = 10; // 한페이지에 보여줄 페이지번호의 개수(일단 10으로 지정)

  // 생성자
  @Builder(builderMethodName = "profileList")
  public PageResponseDTO(List<E> list, PageRequestDTO pageRequestDTO, long totalCount) {
    this.list = list;
    this.pageRequestDTO = pageRequestDTO;
    this.totalCount = totalCount;

    // 화면에 보여줄 마지막 페이지번호
    int endPage = (int) (Math.ceil(pageRequestDTO.getPage() / (double) PAGE_NUM_UNIT)) * PAGE_NUM_UNIT;
    // 화면에 보여줄 첫 페이지 번호
    int startPage = endPage - (PAGE_NUM_UNIT - 1);
    // 최종페이지번호
    int lastPage = (int) (Math.ceil(totalCount / (double) pageRequestDTO.getSize()));
    // endPage 조정
    endPage = endPage > lastPage ? lastPage : endPage;

    this.prev = startPage > 1;
    this.next = totalCount > endPage * pageRequestDTO.getSize();

    this.pageNumList = IntStream.rangeClosed(startPage, endPage).boxed().collect(Collectors.toList());
    this.pageNumLength = this.pageNumList.size();

    if (prev) {
      this.prevPage = startPage - 1;
    }
    if (next) {
      this.nextPage = endPage + 1;
    }

    this.current = this.pageRequestDTO.getPage();
  }

  // 프로필 리스트를 설정하는 메서드 추가
  public static <E> PageResponseDTOBuilder<E> profileList() {
    return new PageResponseDTOBuilder<>();
  }

  public static class PageResponseDTOBuilder<E> {
    private List<E> list;
    private PageRequestDTO pageRequestDTO;
    private long totalCount;

    PageResponseDTOBuilder() {
    }

    public PageResponseDTOBuilder<E> list(List<E> list) {
      this.list = list;
      return this;
    }

    public PageResponseDTOBuilder<E> pageRequestDTO(PageRequestDTO pageRequestDTO) {
      this.pageRequestDTO = pageRequestDTO;
      return this;
    }

    public PageResponseDTOBuilder<E> totalCount(long totalCount) {
      this.totalCount = totalCount;
      return this;
    }

    public PageResponseDTO<E> build() {
      return new PageResponseDTO<>(list, pageRequestDTO, totalCount);
    }

    public String toString() {
      return "PageResponseDTO.PageResponseDTOBuilder(list=" + this.list + ", pageRequestDTO=" + this.pageRequestDTO + ", totalCount=" + this.totalCount + ")";
    }
  }
}

