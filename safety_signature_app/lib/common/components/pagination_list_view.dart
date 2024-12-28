import 'package:flutter/material.dart';
import 'package:flutter_riverpod/flutter_riverpod.dart';
import 'package:go_router/go_router.dart';
import 'package:safety_signature_app/common/const/color.dart';
import 'package:safety_signature_app/common/model/cursor_pagination_model.dart';
import 'package:safety_signature_app/common/model/model_with_id.dart';
import 'package:safety_signature_app/common/provider/pagination_provider.dart';
import 'package:safety_signature_app/common/utils/pagination_utils.dart';

typedef PaginationWidgetBuilder<T extends IModelWithId> = Widget Function(
    BuildContext context, int index, T model);

class PaginationListView<T extends IModelWithId>
    extends ConsumerStatefulWidget {
  final StateNotifierProvider<PaginationProvider, CursorPaginationBase>
      provider;
  final PaginationWidgetBuilder<T> itemBuilder;
  final T? skeletonBuilder;
  const PaginationListView(
      {super.key,
      required this.provider,
      required this.itemBuilder,
      this.skeletonBuilder});

  @override
  ConsumerState<PaginationListView> createState() =>
      _PaginationListViewState<T>();
}

class _PaginationListViewState<T extends IModelWithId>
    extends ConsumerState<PaginationListView> {
  final ScrollController controller = ScrollController();

  @override
  void initState() {
    // TODO: implement initState
    super.initState();
    controller.addListener(listener);
  }

  void listener() {
    PaginationUtils.paginate(
      controller: controller,
      provider: ref.read(widget.provider.notifier),
    );
  }

  @override
  void dispose() {
    controller.removeListener(listener);
    controller.dispose();
    // TODO: implement dispose
    super.dispose();
  }

  @override
  Widget build(BuildContext context) {
    final state = ref.watch(widget.provider);
    // 진짜 초기 로딩 상태,.
    if (state is CursorPaginationLoading) {
      //data.length == 0 확인할 필요가 없음!
      if (widget.skeletonBuilder != null) {
        return Padding(
          padding: const EdgeInsets.symmetric(horizontal: 16.0),
          child: ListView.separated(
            physics: AlwaysScrollableScrollPhysics(),
            controller: controller,
            itemCount: 20 + 1,
            itemBuilder: (con, index) {
              // 테스트 완료후 수정하기
              if (index == 20) {
                return const Padding(
                  padding:
                      EdgeInsets.symmetric(horizontal: 16.0, vertical: 8.0),
                  child: Center(
                      child: CircularProgressIndicator(
                    color: TEXT_COLOR,
                  )),
                );
              }
              return widget.itemBuilder(con, index, widget.skeletonBuilder!);
              // return widget.loadingSkeleton;
            },
            separatorBuilder: (_, index) {
              return const SizedBox(
                height: 16,
              );
            },
          ),
        );
      }
      return const Center(
        child: CircularProgressIndicator(
          color: TEXT_COLOR,
        ),
      );
    }
    //에러
    if (state is CursorPaginationError) {
      return Column(
        crossAxisAlignment: CrossAxisAlignment.stretch,
        mainAxisAlignment: MainAxisAlignment.center,
        children: [
          Text(
            state.message,
            textAlign: TextAlign.center,
          ),
          const SizedBox(
            height: 16.0,
          ),
          ElevatedButton(
              onPressed: () {
                ref
                    .read(
                      widget.provider.notifier,
                    )
                    .paginate(
                      forceRefetch: true,
                    );
              },
              child: Text('다시시도'))
        ],
      );
    }
    //CursorPagination
    //CursorPaginationFetchingMore
    //CursorPaginationRefetching
    final cp = state as CursorPagination<T>;
    return Padding(
        padding: const EdgeInsets.symmetric(horizontal: 16.0),
        child: RefreshIndicator(
          onRefresh: () async {
            ref.read(widget.provider.notifier).paginate(
                  forceRefetch: true,
                );
          },
          child: ListView.separated(
            physics: AlwaysScrollableScrollPhysics(),
            controller: controller,
            itemCount: cp.data.length + 1,
            itemBuilder: (con, index) {
              if (index == cp.data.length) {
                return Padding(
                  padding: const EdgeInsets.symmetric(
                      horizontal: 16.0, vertical: 8.0),
                  child: Center(
                    child: cp is CursorPaginationFetchingMore
                        ? CircularProgressIndicator()
                        : Text('마지막 데이터 입니다 ㅜㅜ'),
                  ),
                );
              }
              final pItem = cp.data[index];
              return widget.itemBuilder(
                context,
                index,
                pItem,
              );
            },
            separatorBuilder: (_, index) {
              return const SizedBox(
                height: 16,
              );
            },
          ),
        ));
  }
}
