import 'package:json_annotation/json_annotation.dart';
part 'common_detail_model.g.dart';

abstract class CommonDetailModelBase {}

class CommonDetailModelError extends CommonDetailModelBase {
  final String message;
  final String httpStatus;
  CommonDetailModelError({required this.message, required this.httpStatus});
}

// 🔹 로딩 상태 (싱글톤 패턴 적용)
class CommonDetailModelLoading extends CommonDetailModelBase {
  static final CommonDetailModelLoading _instance =
      CommonDetailModelLoading._();
  factory CommonDetailModelLoading() => _instance;
  CommonDetailModelLoading._();
}

// 🔹 제네릭을 활용한 공통 상세 모델
@JsonSerializable(genericArgumentFactories: true)
class CommonDetailModel<T> extends CommonDetailModelBase {
  final T data;

  CommonDetailModel({required this.data});

  CommonDetailModel<T> copyWith({T? data}) {
    return CommonDetailModel<T>(data: data ?? this.data);
  }

  factory CommonDetailModel.fromJson(
          Map<String, dynamic> json, T Function(Object? json) fromJsonT) =>
      _$CommonDetailModelFromJson(json, fromJsonT);
}

// 🔹 데이터 새로고침 (기존 데이터 유지)
class CommonDetailModelRefetching<T> extends CommonDetailModel<T> {
  final bool isRefetching;

  CommonDetailModelRefetching({
    required super.data,
    this.isRefetching = true,
  });
}

// 🔹 추가 데이터 요청 (기존 데이터 유지)
class CommonDetailModelFetchingMore<T> extends CommonDetailModel<T> {
  final bool isFetchingMore;

  CommonDetailModelFetchingMore({
    required super.data,
    this.isFetchingMore = true,
  });
}
