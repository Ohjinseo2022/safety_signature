import moment, { unitOfTime, type DurationInputArg2 } from "moment";
import "moment/locale/ko";



/**
 * @param time
 * @param format
 * @returns {fortmat 형태의 date string 반환}
 */
export const date_string = (
  time: Date = new Date(),
  format: string = "YYYY-MM-DD HH:mm"
): string => {
  return moment(time).format(format);
};

/**
 * @param time
 * @param format
 * @returns {format 형태의 time을 Date 반환}
 */
export const string_date = (
  time: string,
  format: string = "YYYY-MM-DD HH:mm"
): Date => {
  return moment(time, format).toDate();
};

/**
 *
 * @param time
 * @param format
 * @returns {time형태의 string을 format 형태로 반환}
 */
export const dateFormat = (
  time: string = "",
  format: string = "YYYY-MM-DD"
): string => {
  return moment(time).format(format);
};

/**
 *
 * @param BaseDay
 * @param plusNumber
 * @param format
 * @returns {BaseDay 기준 plusNumber 만큼의 날짜 계산 format 형태로 반환}
 */
export const datePlus = (
  BaseDay: string,
  plusNumber: string,
  format: string = "YYYY-MM-DD"
): string => {
  return moment(BaseDay, format).add(plusNumber, "days").format(format);
};

/**
 *
 * @param BaseDay
 * @param plusNumber
 * @param format
 * @param period
 * @returns {BaseDay 기준 period과 plusNumber 만큼의 날짜 계산 format 형태로 반환}
 */
export const customDatePlus = (
  BaseDay: string,
  plusNumber: string,
  period: unitOfTime.Base = "days",
  format: string = "YYYY-MM-DD"
): string => {
  return moment(BaseDay, format).add(plusNumber, period).format(format);
};

/**
 *
 * @param time
 * @param before_format
 * @param after_format
 * @returns {before_format 형태의 date, after_format 으로 변경}
 */
export const data_format_chg = (
  time: string,
  before_format: string = "YYYY-MM-DD HH:mm",
  after_format: string = "YYYYMMDD"
): string => {
  return moment(time, before_format).format(after_format);
};

/**
 *
 * @param format
 * @returns {format 형태의 현재시간}
 */
export const nowDate = (format: string = "YYYY-MM-DD HH:mm"): string => {
  return moment().format(format);
};

/**
 * 이번달 1일 구하기
 * @param format
 * @return {str 이번달 1일}
 */
export const firstDayMonth = (format: string = "YYYY-MM-DD") => {
  return moment().startOf("month").format(format);
};

/**
 * 선택한 date 의 1일 구하기
 * @param format
 * @return {str 이번달 1일}
 */
export const firstDateStartOf = (
  date: string,
  format: string = "YYYY-MM-DD"
) => {
  return moment(date).startOf("month").format(format);
};

/**
 * 이번달 말일 구하기
 * @param date
 * @param format
 * @return {str 이번달 말일}
 */
export const endDayMonth = (
  date: string = "",
  format: string = "YYYY-MM-DD"
) => {
  return moment(date).endOf("month").format(format);
};

/**
 * 요일 구하기
 * @param date
 * @param format 현재 포멧
 * @returns {요일}
 */
export const getDay = (
  date: string = "",
  format: string = "YYYY-MM-DD"
): string => {
  let day;
  const dddd = moment(date, format).format("dddd");

  if (dddd === "Sunday") {
    day = "일";
  } else if (dddd === "Monday") {
    day = "월";
  } else if (dddd === "Tuesday") {
    day = "화";
  } else if (dddd === "Wednesday") {
    day = "수";
  } else if (dddd === "Thursday") {
    day = "목";
  } else if (dddd === "Friday") {
    day = "금";
  } else if (dddd === "Saturday") {
    day = "토";
  } else {
    day = "";
  }
  return day;
};

/**
 * type에따라 날짜 or 시간 더하기
 * @param datTime
 * @param format
 * @param type
 */
export const addDateTime = (
  datTime: string,
  format: string,
  val: number,
  type: DurationInputArg2
): string => {
  return moment(moment(datTime, format).add(val, type)).format(format);
};

/**
 * type에따라 날짜 or 시간 빼기
 * @param datTime
 * @param format
 * @param type
 */
export const subDateTime = (
  datTime: string,
  format: string,
  val: number,
  type: DurationInputArg2
): string => {
  return moment(moment(datTime, format).subtract(val, type)).format(format);
};
/**
 * 시작날짜 와 끝날짜의 일수 차이
 * @param startDate
 * @param endDate
 */
export const durationDay = (startDate: string, endDate: string): number => {
  return moment.duration(moment(endDate).diff(startDate)).asDays();
};
/**
 * 시작날짜 와 끝날짜의 개월수 차이
 * @param startDate
 * @param endDate
 */
export const durationMonth = (startDate: string, endDate: string): number => {
  return (
    Math.floor(moment.duration(moment(endDate).diff(startDate)).asMonths()) + 1
  );
};

/**
 * @param count
 * @param pagerSize
 * @param showFLPage
 * @returns {pagerSize 와 count 를 계산하여 페이징 처리}
 */
export const pagerSet = (
  count: number,
  pagerSize: string,
  showFLPage: boolean = true
): {
  showFirstLastPage: boolean;
  totalVisible: number;
  length: number;
} => {
  let visible = 0;
  let totallength = 0;
  if (count / parseInt(pagerSize) < 1) {
    visible = 2;
    totallength = 1;
  } else if (count % parseInt(pagerSize) === 0) {
    visible = 10;
    totallength = Math.floor(count / parseInt(pagerSize));
  } else if (count / parseInt(pagerSize) < 10) {
    visible = Math.floor(count / parseInt(pagerSize) + 1);
    totallength = Math.floor(count / parseInt(pagerSize) + 1);
  } else {
    visible = 10;
    totallength = Math.floor(count / parseInt(pagerSize)) + 1;
  }
  return {
    showFirstLastPage: showFLPage,
    totalVisible: visible,
    length: totallength,
  };
};

/**
 *
 * @param conut
 * @param index
 * @param pageNumber
 * @param pageSize
 * @returns {conut와 현재 pageNumber 확인하여 번호 역순으로 처리}
 */
export const reverseNum = (
  conut: number,
  index: number,
  pageNumber: number,
  pageSize: number
): number => {
  return conut - index - (pageNumber - 1) * pageSize;
};

/**
 * @param obj T
 * @returns { T Object Deep Copy }
 */
export const deepCopy = <T = unknown>(obj: T): T => {
  if (typeof obj !== "object" || obj === null) {
    return obj;
  }

  type Keys = Partial<string | number | symbol>;
  let copy: Record<Keys, any>;

  if (Array.isArray(obj)) {
    copy = obj.map(deepCopy);
  } else {
    copy = {};
    Object.keys(obj).forEach((key: string) => {
      if (Object.prototype.hasOwnProperty.call(obj, key)) {
        copy[key] = deepCopy(obj[key as keyof typeof obj]);
      }
    });
  }

  return copy as T;
};

/**
 * 문자 maxLength 이상 일경우 말줄임
 * @param str string
 * @param maxLength
 * @returns {str str....}
 */
export const strOmit = (str: string|number, maxLength: number = 50): string => {
  if (str === null || str === undefined) return "";
  let tempStr = str
  if(typeof tempStr ==='number') tempStr = tempStr.toString()
  if (tempStr.length < maxLength) {
    return tempStr;
  }
  return tempStr.substring(0, maxLength) + "....";
};

// /**
//  * comboBox 전체 추가
//  * @param items
//  * @returns
//  */
// export const comboAll = (
//   items: ComboItems[],
//   allValue: string = ""
// ): ComboItems[] => {
//   return [{ id: allValue, text: "전체" }, ...items];
// };

/**
 * {a: 1, b: {c: 1}} => {a: 1, b_c: 1} 형식으로 flat object 변경
 * @param obj
 * @param parKey
 * @returns {Object, flat object 변경}
 */
export const objectFlat = <T>(obj: Object, parKey = ""): T => {
  let result: { [key: string]: any } = {};

  for (const [key, value] of Object.entries(obj)) {
    const _key = parKey ? `${parKey}_${key}` : key;
    if (value && typeof value === "object") {
      result = { ...result, ...objectFlat(value, _key) };
    } else {
      result[_key] = value;
    }
  }

  return result as T;
};

/**
 * 그리드 no 값 추가
 * @param array
 * @param curPage
 * @param pageSize
 * @returns { Type T Array }
 */
export const gridNumbering = <T>(
  array: T,
  curPage: string | number,
  pageSize: string | number
): T => {
  const cp = Number(curPage);
  const ps = Number(pageSize);

  if (!(array instanceof Array)) return array;
  return array.map((x: any, idx: number) => ({
    ...x,
    no: cp * (ps - 1) + idx + 1,
  })) as T;
};

/**
 * 그리드 no 값 추가 DESC
 * @param array
 * @param curPage
 * @param pageSize
 * @param count
 * @returns { Type T Array }
 */
export const gridNumberingDesc = <T>(
  array: T,
  curPage: string | number,
  pageSize: string | number,
  count: string | number
): T => {
  const cp = Number(curPage);
  const ps = Number(pageSize);
  const co = Number(count);
  if (!(array instanceof Array)) return array;
  return array.map((x: any, idx: number) => {
    return { ...x, no: co - (ps - 1) * cp - idx };
  }) as T;
};

/**
 *
 * @param arr T[]
 * @param key string
 * @returns { groupBy key값으로 그룹  }
 */
export const groupBy = <T extends Record<string, any>>(
  arr: T[],
  key: string
): Record<string, T[]> => {
  return arr.reduce((acc: Record<string, T[]>, cur: T) => {
    const keyValue = cur[key];
    if (!acc[keyValue]) {
      acc[keyValue] = [];
    }
    acc[keyValue].push(cur);
    return acc;
  }, {});
};

/**
 *  Object 같은지 비교
 * @param obj1 비교대상1
 * @param obj2 비교대상1
 * @returns { boolean }
 */
export const objectEqual = (obj1: any, obj2: any): boolean => {
  if (obj1 === obj2) {
    return true;
  }

  if (
    typeof obj1 !== "object" ||
    obj1 === null ||
    typeof obj2 !== "object" ||
    obj2 === null
  ) {
    return false;
  }

  const keys1 = Object.keys(obj1);
  const keys2 = Object.keys(obj2);

  if (keys1.length !== keys2.length) {
    return false;
  }

  for (const key of keys1) {
    if (!keys2.includes(key) || !objectEqual(obj1[key], obj2[key])) {
      return false;
    }
  }

  return true;
};

/**
 * object 에서 keyToRemove 키 제거
 * @param obj
 * @param keyToRemove
 * @returns
 */
export const removeKey = (
  obj: Record<string, any>,
  keyToRemove: string
): Record<string, any> => {
  const newObj = { ...obj };
  delete newObj[keyToRemove];
  return newObj;
};

/**
 * 배열에서 인덱스 변경
 * @param arr T[]
 * @param currentIndex 현재 index
 * @param newIndex  원하는 index
 * @returns {T[]}
 */
export const arrMoveIndex = <T>(
  arr: T[],
  currentIndex: number,
  newIndex: number
): T[] => {
  if (
    currentIndex < 0 ||
    newIndex < 0 ||
    currentIndex >= arr.length ||
    newIndex >= arr.length
  ) {
    return arr;
  }

  const newArr = arr.slice();
  const [movedElement] = newArr.splice(currentIndex, 1);
  newArr.splice(newIndex, 0, movedElement);

  return newArr;
};

/**
 * tree 데이터, children을 flat 하게 반환
 * @param tree
 * @returns { T[] }
 */
export const flatNodes = <T extends { children: T[] }>(tree: T[]): T[] => {
  const flatData: T[] = [];

  const flatten = (node: T) => {
    const flatNode = { ...node };
    flatData.push(flatNode);

    if (node.children) {
      for (const child of node.children) {
        flatten(child);
      }
    }
  };

  for (const node of tree) {
    flatten(node);
  }

  return flatData;
};

/**
 * 트리데이터 구성
 * @param data
 * @returns
 */
export const makeTree = <
  T extends { children: T[]; topMenuId: string; id: string }
>(
  data: T[],
  setTopMenuId: string = "Main"
): T[] => {
  const idToNodeMap: { [key: string]: T } = {};
  const rootNodes: T[] = [];

  data.forEach((item) => {
    const { id, topMenuId } = item;
    const treeNode = { ...item, children: [] } as T;
    idToNodeMap[id] = treeNode;

    if (topMenuId === setTopMenuId) {
      rootNodes.push(treeNode);
    } else {
      const parentTreeNode = idToNodeMap[topMenuId];
      if (parentTreeNode) {
        parentTreeNode.children?.push(treeNode);
      }
    }
  });

  return rootNodes;
};

/**
 * 첨부 파일 등록시 기존 value 제거
 * @param val
 */
export const clearFileVal = (val: any): void => {
  val.target.value = null;
};

/* publishing 퍼블리싱 모션 추가 작업 */
/* 페이지 대입
onMounted(()=> {
  pubAnimation();
});
*/

export const pubAnimation = () => {
  let observer = new IntersectionObserver((entries) => {
    entries.forEach((entry) => {
      const target = entry.target;
      if (target instanceof HTMLElement) {
        const animation = target.dataset.animation;
        if (animation) {
          const aniInfo = animation.split(",");
          let sclT = window.scrollY;
          let top = target.getBoundingClientRect().top;
          if (!entry.isIntersecting) {
            sclT = window.scrollY;
            if (sclT + top >= sclT) {
              target.classList.remove("animated", aniInfo[0]);
              return;
            }
          }
          target.classList.add("animated", aniInfo[0]);
          if (aniInfo[1]) {
            target.style.cssText = `animation-delay:${aniInfo[1]}ms`;
          }
          if (aniInfo[2]) {
            target.style.cssText = `animation-duration:${aniInfo[2]}ms`;
          }
        }
      }
    });
  });
  const ani = document.querySelectorAll("[data-animation]");
  ani.forEach((elem) => {
    observer.observe(elem);
  });
};

