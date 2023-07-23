package kr.co.prgrms.clone.global.error;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
    // todo : 에러코드 분할하기 : 에러코드 다시 정리하기
    INVALID_PARAMETER_VALUE(-400, "잘못된 파라미터를 입력하셨습니다."),

    USER_SAVE_FAIL(-500, "회원정보를 저장하는데 실패했습니다."),
    DUPLICATE_USER(-400, "이미 가입된 회원입니다."),
    USER_NOT_FOUND(-404, "가입된 회원정보를 찾을 수 없습니다."),
    USER_NOT_LOG_IN(-400, "유저가 로그인 할 수 없습니다."),
    INCORRECT_PASSWORD(-400, "비밀번호가 일치하지 않습니다."),

    INCORRECT_MEMBERSHIP_PURCHASE(-400, "다른 종류의 맴버십을 구매할 수 없습니다."),

    CREDIT_SAVE_FAIL(-500, "크레딧을 구매하는데 실패했습니다."),
    CREDIT_UPDATE_FAIL(-500, "크레딧을 추가구매하는데 실패했습니다."),
    CREDIT_NOT_FOUND(-404, "크레딧 정보를 찾을 수 없습니다."),
    ILLEGAL_CREDIT_AMOUNT_PURCHASE(-400, "구매하려는 크레딧의 양이 잘못되었습니다."),

    SUBSCRIPTION_SAVE_FAIL(-500, "구독권 구매에 실패했습니다."),
    SUBSCRIPTION_UPDATE_FAIL(-500, "구독권 연장에 실패했습니다."),
    SUBSCRIPTION_NOT_FOUND(-404, "구독권 정보를 찾을 수 없습니다."),
    ILLEGAL_SUBSCRIPTION_MONTH_PURCHASE(-400, "구독권의 연장일이 잘못되었습니다."),

    NOT_FOUND_SONG(-404, "곡의 검색결과가 없습니다."),

    NOT_FOUND_ARTIST(-404, "아티스트를 찾을 수 없습니다."),
    NOT_FOUND_ARTIST_IMAGE(-404, "아티스트 이미지 파일을 찾을 수 없습니다."),

    NOT_CREATE_PLAYLIST(-500, "플레이리스트를 생성할 수 없습니다."),
    NOT_ADD_IN_PLAYLIST(-500, "플레이리스트에 곡을 추가할 수 없습니다."),
    NOT_FOUND_PLAYLIST(-404, "플레이리스트를 찾을 수 없습니다."),

    NOT_INSERT_ORDER(-500, "음악을 구매품목에 추가할 수 없습니다."),
    SHORT_OF_TOTAL_CREDITS(-400, "음악을 구매하기에 크레딧의 양이 부족합니다."),
    ORDER_DELETE_FAIL(-500, "주문에 실패했습니다."),
    ORDER_FOUND_FAIL(-400, "주문을 불러오는데 실패했습니다."),
    NOT_CREATE_ORDER(-500, "주문을 생성하는데 실패했습니다."),
    USER_NOT_UPDATE(-500, "유저정보를 업데이트 할 수 없습니다."),
    NO_VALID_GENRE(-500, "잘못된 장르입니다."),
    ALREADY_EXIST(-400, "이미 장바구니에 존재하는 곡입니다.");

    private final int status;
    private final String message;
}
