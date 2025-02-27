import 'package:json_annotation/json_annotation.dart';

part 'pagination_params.g.dart';

@JsonSerializable()
class PaginationParams {
  final String? nextCursor;
  final int? count;

  const PaginationParams({this.nextCursor, this.count});

  PaginationParams copyWith({
    String? nextCursor,
    int? count,
  }) {
    return PaginationParams(
      nextCursor: nextCursor ?? this.nextCursor,
      count: count ?? this.count,
    );
  }

  factory PaginationParams.fromJson(Map<String, dynamic> json) =>
      _$PaginationParamsFromJson(json);

  Map<String, dynamic> toJson() => _$PaginationParamsToJson(this);
}
