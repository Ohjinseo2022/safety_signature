import 'package:json_annotation/json_annotation.dart';
part 'common_detail_model.g.dart';

abstract class CommonDetailModelBase {}

class CommonDetailModelError extends CommonDetailModelBase {
  final String message;
  final String httpStatus;
  CommonDetailModelError({required this.message, required this.httpStatus});
}

class CommonDetailModelLoading extends CommonDetailModelBase {}

@JsonSerializable(
  genericArgumentFactories: true,
)
class CommonDetailModel<T> extends CommonDetailModelBase {
  final T data;
  CommonDetailModel({required this.data});

  CommonDetailModel copyWith({
    T? data,
  }) {
    return CommonDetailModel<T>(data: data ?? this.data);
  }

  factory CommonDetailModel.fromJson(
          Map<String, dynamic> json, T Function(Object? json) fromJsonT) =>
      _$CommonDetailModelFromJson(json, fromJsonT);
}

class CommonDetailModelRefetching<T> extends CommonDetailModel<T> {
  CommonDetailModelRefetching({required super.data});
}

class CommonDetailModelFetchingMore<T> extends CommonDetailModel<T> {
  CommonDetailModelFetchingMore({required super.data});
}
