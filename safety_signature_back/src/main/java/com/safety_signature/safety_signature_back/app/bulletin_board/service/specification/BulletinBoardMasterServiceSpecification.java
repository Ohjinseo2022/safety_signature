package com.safety_signature.safety_signature_back.app.bulletin_board.service.specification;

import com.safety_signature.safety_signature_back.app.bulletin_board.domain.BulletinBoardMaster;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class BulletinBoardMasterServiceSpecification {
    private final Logger log = LoggerFactory.getLogger(BulletinBoardMasterServiceSpecification.class);
    @Getter
    @AllArgsConstructor
    public enum BulletinBoardMasterSearchCondition{
        BOARD_TITLE("게시글 제목"),
        CREATED_BY("내가쓴글인지"),
        USER_ID("작성자"),
        START_DATE("검색시작일"),
        END_DATE("검색종료일");
        private String value;
    }
    public static Specification<BulletinBoardMaster> getSpecification(Map<BulletinBoardMasterSearchCondition,Object> condition) {
        return ((root, query, builder) -> {
            List<Predicate> predicate =getPredicates(condition,root,builder,query);
            return predicate.isEmpty() ? builder.conjunction() : builder.and(predicate.toArray(new Predicate[0]));
        });
    }

    private static List<Predicate> getPredicates(Map<BulletinBoardMasterSearchCondition,Object> condition, Root<BulletinBoardMaster> root, CriteriaBuilder builder, CriteriaQuery<?> query){
        List<Predicate> predicate = new ArrayList<>();
        for(BulletinBoardMasterSearchCondition key : condition.keySet()){
            switch (key){
                case BOARD_TITLE:
                    predicate.add(builder.like(root.get("boardTitle"),"%"+condition.get(key).toString()+"%"));
                    break;
                case CREATED_BY:
                    predicate.add(builder.equal(root.get("createdBy"), condition.get(key)));
                    break;
                case USER_ID:
                    predicate.add(
                            builder.equal(root.get("userMasterId"),condition.get(key).toString())
                    );
                    break;
                case START_DATE:
                    predicate.add(builder.greaterThanOrEqualTo(root.get("createdDate"), LocalDate.parse(String.valueOf(condition.get(key)), DateTimeFormatter.ofPattern("yyyy-MM-dd")).atStartOfDay().atZone(ZoneId.of("Asia/Seoul")).toInstant()));
                    break;
                case END_DATE:
                    predicate.add(builder.lessThan(root.get("createdDate"), LocalDate.parse(String.valueOf(condition.get(key)), DateTimeFormatter.ofPattern("yyyy-MM-dd")).atStartOfDay().atZone(ZoneId.of("Asia/Seoul")).toInstant()));
                    break;
                default:
                   break;
            }
        }

        return predicate;
    }
}
