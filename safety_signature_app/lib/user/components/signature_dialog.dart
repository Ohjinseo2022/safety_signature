import 'dart:typed_data';
import 'dart:ui';

import 'package:flutter/material.dart';
import 'package:hand_signature/signature.dart';
import 'package:safety_signature_app/common/components/common_button.dart';
import 'package:safety_signature_app/common/components/common_dialog.dart';
import 'package:safety_signature_app/common/const/color.dart';
import 'package:syncfusion_flutter_signaturepad/signaturepad.dart';

Future<void> signatureDialog(
    {required BuildContext context,
    String? title,
    required Function(Uint8List value) onConfirm,
    VoidCallback? onCancel,
    bool barrierDismissible = false}) async {
  GlobalKey<SfSignaturePadState> signaturePadKey = GlobalKey();
  bool isSubmit = false;
  bool isSignature = false;
  Uint8List? image;

  return showDialog<void>(
    context: context,
    barrierDismissible: barrierDismissible, // 다이얼로그 외부를 눌러도 닫히지 않도록 설정
    builder: (
      BuildContext context,
    ) =>
        StatefulBuilder(
      builder: (BuildContext context, StateSetter setDialog) {
        return AlertDialog(
          backgroundColor: BACK_GROUND_COLOR,
          title: Text(
            "전자 서명 등록",
            style: defaultTextStyle,
          ),
          content: SingleChildScrollView(
            child: ListBody(
              children: <Widget>[
                GestureDetector(
                  onPanUpdate: (details) => {},
                  child: Container(
                    height: 150,
                    width: MediaQuery.of(context).size.width * 0.9,
                    // color: Colors.black26,
                    child: Stack(children: [
                      Container(
                        height: 150,
                        width: MediaQuery.of(context).size.width * 0.9,
                        color: BORDER_COLOR,
                      ),
                      if (!isSignature)
                        Center(
                            child: Text(
                          "여기에 서명을 해주세요",
                          style: defaultTextStyle,
                        )),
                      SfSignaturePad(
                        onDraw: (offset, time) async {
                          if (image == null && !isSignature) {
                            setDialog(() {
                              isSignature = !isSignature;
                            });
                          }
                        },
                        //고유 키값 지정
                        key: signaturePadKey,
                        minimumStrokeWidth: 1,
                        maximumStrokeWidth: 5,

                        strokeColor: Colors.black,
                        backgroundColor: Colors.transparent,
                      ),
                    ]),

                    // HandSignature(
                    //   control: control,
                    //   color: Colors.black,
                    //   width: 1.0,
                    //   maxWidth: 10.0,
                    //   type: SignatureDrawType.shape,
                    // ),
                  ),
                ),
              ],
            ),
          ),
          actions: [
            CommonButton(
                label: '초기화',
                onPressed: () {
                  setDialog(() {
                    signaturePadKey.currentState?.clear();
                    image = null;
                    isSignature = false;
                  });
                }),
            CommonButton(
                label: '확인',
                onPressed: () async {
                  if (isSignature) {
                    commonDialog(
                        context: context,
                        content: Container(
                            height: 50,
                            alignment: Alignment.bottomCenter,
                            child: const Text(
                              "서명 정보는 등록 후 수정 할수 없습니다. 회원가입 진행 할까요?",
                              style: defaultTextStyle,
                            )),
                        onConfirm: () async {
                          final image =
                              await signaturePadKey.currentState!.toImage();
                          ByteData? byteData = (await image.toByteData(
                              format: ImageByteFormat.png)) as ByteData?;

                          Uint8List? pngBytes = byteData?.buffer.asUint8List();
                          onConfirm(pngBytes!);
                          Navigator.of(context).pop();
                        },
                        onCancel: () {},
                        barrierDismissible: true);
                  } else {
                    commonDialog(
                        context: context,
                        content: Container(
                            height: 50,
                            alignment: Alignment.bottomCenter,
                            child: Text(
                              "서명은 필수 값입니다.",
                              style: defaultTextStyle,
                            )),
                        onConfirm: () {},
                        barrierDismissible: true);
                  }
                }),
            CommonButton(
                label: '최소',
                onPressed: () {
                  Navigator.of(context).pop();
                }),
          ],
        );
      },
    ),
  );
}
