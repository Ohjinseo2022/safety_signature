import React from 'react'

const PrivacyPolicy = () => {
  return (
    <div className="relative h-max min-h-full px-[60px] pb-[100px] bg-white">
      <p className="text-center text-2xl font-bold py-[52px]">
        개인정보처리방침
      </p>

      <section>
        <p className="provision_title text-xl font-bold mt-6">
          <strong>제1조(본 방침의 공개)</strong>
        </p>
        <ul className="list-disc pl-5">
          <li>
            회사는 이용자가 언제든지 쉽게 본 방침을 확인할 수 있도록 회사
            홈페이지 첫 화면 또는 첫 화면과의 연결화면을 통해 본 방침을 공개하고
            있습니다.
          </li>
          <li>
            회사는 제1항에 따라 본 방침을 공개하는 경우 글자 크기, 색상 등을
            활용하여 이용자가 본 방침을 쉽게 확인할 수 있도록 합니다.
          </li>
        </ul>
      </section>

      <section>
        <p className="provision_title text-xl font-bold mt-6">
          <strong>제2조(본 방침의 변경)</strong>
        </p>
        <ul className="list-disc pl-5">
          <li>
            본 방침은 개인정보 관련 법령, 지침, 고시 또는 정부나 회사 서비스의
            정책이나 내용의 변경에 따라 개정될 수 있습니다.
          </li>
          <li>
            회사는 제1항에 따라 본 방침을 개정하는 경우 다음 각 호 하나 이상의
            방법으로 공지합니다.
            <ul className="list-disc pl-5">
              <li>
                회사가 운영하는 인터넷 홈페이지의 첫 화면의 공지사항란 또는
                별도의 창을 통하여 공지하는 방법
              </li>
              <li>
                서면·모사전송·전자우편 또는 이와 비슷한 방법으로 이용자에게
                공지하는 방법
              </li>
            </ul>
          </li>
          <li>
            회사는 제2항의 공지는 본 방침 개정의 시행일로부터 최소 7일 이전에
            공지합니다. 다만, 이용자 권리의 중요한 변경이 있을 경우에는 최소
            30일 전에 공지합니다.
          </li>
        </ul>
      </section>

      <section>
        <p className="provision_title text-xl font-bold mt-6">
          <strong>제3조(회원 가입을 위한 정보)</strong>
        </p>
        <p>
          회사는 이용자의 회사 서비스에 대한 회원가입을 위하여 다음과 같은
          정보를 수집합니다.
        </p>
        <ul className="list-disc pl-5">
          <li>
            필수 수집 정보:{' '}
            <span style={{ fontWeight: 'bold', textDecoration: 'underline' }}>
              이메일 주소, 이름 및 휴대폰 번호
            </span>
          </li>
        </ul>
      </section>

      <section>
        <p className="provision_title text-xl font-bold mt-6">
          <strong>제4조(개인정보 수집 방법)</strong>
        </p>
        <p>회사는 다음과 같은 방법으로 이용자의 개인정보를 수집합니다.</p>
        <ul className="list-disc pl-5">
          <li>이용자가 회사의 홈페이지에 자신의 개인정보를 입력하는 방식</li>
          <li>
            어플리케이션 등 회사가 제공하는 홈페이지 외의 서비스를 통해 이용자가
            자신의 개인정보를 입력하는 방식
          </li>
          <li>
            <span style={{ fontWeight: 'bold', textDecoration: 'underline' }}>
              이용자가 고객센터의 상담, 게시판에서의 활동 등 회사의 서비스를
              이용하는 과정에서 이용자가 입력하는 방식
            </span>
          </li>
        </ul>
      </section>

      {/* 이후 제5조 ~ 제19조까지 전달해주신 원본 데이터를 그대로 JSX로 변환하여 추가 */}

      <p className="provision_title text-xl font-bold mt-6">
        <strong>제5조(개인정보의 이용)</strong>
      </p>
      <p>회사는 개인정보를 다음 각 호의 경우에 이용합니다.</p>
      <ul className="list-disc pl-5">
        <li>공지사항의 전달 등 회사 운영에 필요한 경우</li>
        <li>
          이용문의에 대한 회신, 불만의 처리 등 이용자에 대한 서비스 개선을 위한
          경우
        </li>
        <li>회사의 서비스를 제공하기 위한 경우</li>
        <li>
          법령 및 회사 약관을 위반하는 회원에 대한 이용 제한 조치, 부정 이용
          행위를 포함하여 서비스의 원활한 운영에 지장을 주는 행위에 대한 방지 및
          제재를 위한 경우
        </li>
        <li>
          <span style={{ fontWeight: 'bold', textDecoration: 'underline' }}>
            개인정보 및 관심에 기반한 이용자 간 관계의 형성을 위한 경우
          </span>
        </li>
      </ul>

      <p className="provision_title text-xl font-bold mt-6">
        <strong>제6조(개인정보의 보유 및 이용기간)</strong>
      </p>
      <ul className="list-disc pl-5">
        <li>
          회사는 이용자의 개인정보에 대해 개인정보의 수집·이용 목적 달성을 위한
          기간 동안 개인정보를 보유 및 이용합니다.
        </li>
        <li>
          전항에도 불구하고 회사는 내부 방침에 의해 서비스 부정이용기록은 부정
          가입 및 이용 방지를 위하여 회원 탈퇴 시점으로부터 최대 1년간
          보관합니다.
        </li>
      </ul>

      <p className="provision_title text-xl font-bold mt-6">
        <strong>제7조(법령에 따른 개인정보의 보유 및 이용기간)</strong>
      </p>
      <p>회사는 관계법령에 따라 다음과 같이 개인정보를 보유 및 이용합니다.</p>
      <ul className="list-disc pl-5">
        <li>
          전자상거래 등에서의 소비자보호에 관한 법률에 따른 보유정보 및 보유기간
          <ul className="list-disc pl-5">
            <li>계약 또는 청약철회 등에 관한 기록 : 5년</li>
            <li>대금결제 및 재화 등의 공급에 관한 기록 : 5년</li>
            <li>소비자의 불만 또는 분쟁처리에 관한 기록 : 3년</li>
            <li>표시•광고에 관한 기록 : 6개월</li>
          </ul>
        </li>
        <li>
          통신비밀보호법에 따른 보유정보 및 보유기간
          <ul className="list-disc pl-5">
            <li>웹사이트 로그 기록 자료 : 3개월</li>
          </ul>
        </li>
        <li>
          전자금융거래법에 따른 보유정보 및 보유기간
          <ul className="list-disc pl-5">
            <li>전자금융거래에 관한 기록 : 5년</li>
          </ul>
        </li>
        <li>
          위치정보의 보호 및 이용 등에 관한 법률
          <ul className="list-disc pl-5">
            <li>개인위치정보에 관한 기록 : 6개월</li>
          </ul>
        </li>
      </ul>

      <p className="provision_title text-xl font-bold mt-6">
        <strong>제8조(개인정보의 파기원칙)</strong>
      </p>
      <p>
        회사는 원칙적으로 이용자의 개인정보 처리 목적의 달성, 보유·이용기간의
        경과 등 개인정보가 필요하지 않을 경우에는 해당 정보를 지체 없이
        파기합니다.
      </p>

      <p className="provision_title text-xl font-bold mt-6">
        <strong>제9조(개인정보파기절차)</strong>
      </p>
      <ul className="list-disc pl-5">
        <li>
          이용자가 회원가입 등을 위해 입력한 정보는 개인정보 처리 목적이 달성된
          후 별도의 DB로 옮겨져 일정 기간 저장된 후 파기됩니다.
        </li>
        <li>
          회사는 파기 사유가 발생한 개인정보를 개인정보보호 책임자의 승인절차를
          거쳐 파기합니다.
        </li>
      </ul>

      <p className="provision_title text-xl font-bold mt-6">
        <strong>제10조(개인정보파기방법)</strong>
      </p>
      <p>
        회사는 전자적 파일형태로 저장된 개인정보는 기록을 재생할 수 없는 기술적
        방법을 사용하여 삭제하며, 종이로 출력된 개인정보는 분쇄기로 분쇄하거나
        소각 등을 통하여 파기합니다.
      </p>
      <p className="provision_title text-xl font-bold mt-6">
        <strong>제11조(광고성 정보의 전송 조치)</strong>
      </p>
      <ul className="list-disc pl-5">
        <li>
          회사는 전자적 전송매체를 이용하여 영리목적의 광고성 정보를 전송하는
          경우 이용자의 명시적인 사전 동의를 받습니다. 다만, 다음 각 호 어느
          하나에 해당하는 경우에는 사전 동의를 받지 않습니다.
          <ul className="list-disc pl-5">
            <li>
              회사가 재화 등의 거래관계를 통하여 수신자로부터 직접 연락처를
              수집한 경우, 거래가 종료된 날로부터 6개월 이내에 회사가 처리하고
              수신자와 거래한 것과 동종의 재화 등에 대한 영리목적의 광고성
              정보를 전송하려는 경우
            </li>
            <li>
              「방문판매 등에 관한 법률」에 따른 전화권유판매자가 육성으로
              수신자에게 개인정보의 수집출처를 고지하고 전화권유를 하는 경우
            </li>
          </ul>
        </li>
        <li>
          회사는 전항에도 불구하고 수신자가 수신거부의사를 표시하거나 사전
          동의를 철회한 경우에는 영리목적의 광고성 정보를 전송하지 않으며
          수신거부 및 수신동의 철회에 대한 처리 결과를 알립니다.
        </li>
        <li>
          회사는 오후 9시부터 그다음 날 오전 8시까지의 시간에 전자적 전송매체를
          이용하여 영리목적의 광고성 정보를 전송하는 경우에는 제1항에도 불구하고
          그 수신자로부터 별도의 사전 동의를 받습니다.
        </li>
        <li>
          회사는 전자적 전송매체를 이용하여 영리목적의 광고성 정보를 전송하는
          경우 다음의 사항 등을 광고성 정보에 구체적으로 밝힙니다.
          <ul className="list-disc pl-5">
            <li>회사명 및 연락처</li>
            <li>수신 거부 또는 수신 동의의 철회 의사표시에 관한 사항의 표시</li>
          </ul>
        </li>
        <li>
          회사는 전자적 전송매체를 이용하여 영리목적의 광고성 정보를 전송하는
          경우 다음 각 호의 어느 하나에 해당하는 조치를 하지 않습니다.
          <ul className="list-disc pl-5">
            <li>
              광고성 정보 수신자의 수신거부 또는 수신동의의 철회를 회피·방해하는
              조치
            </li>
            <li>
              숫자·부호 또는 문자를 조합하여 전화번호·전자우편주소 등 수신자의
              연락처를 자동으로 만들어 내는 조치
            </li>
            <li>
              영리목적의 광고성 정보를 전송할 목적으로 전화번호 또는
              전자우편주소를 자동으로 등록하는 조치
            </li>
            <li>
              광고성 정보 전송자의 신원이나 광고 전송 출처를 감추기 위한 각종
              조치
            </li>
            <li>
              영리목적의 광고성 정보를 전송할 목적으로 수신자를 기망하여 회신을
              유도하는 각종 조치
            </li>
          </ul>
        </li>
      </ul>

      <p className="provision_title text-xl font-bold mt-6">
        <strong>제12조(아동의 개인정보보호)</strong>
      </p>
      <ul className="list-disc pl-5">
        <li>
          회사는 만 14세 미만 아동의 개인정보 보호를 위하여 만 14세 이상의
          이용자에 한하여 회원가입을 허용합니다.
        </li>
        <li>
          제1항에도 불구하고 회사는 이용자가 만 14세 미만의 아동일 경우에는, 그
          아동의 법정대리인으로부터 그 아동의 개인정보 수집, 이용, 제공 등의
          동의를 받습니다.
        </li>
        <li>
          제2항의 경우 회사는 그 법정대리인의 이름, 생년월일, 성별,
          중복가입확인정보(ID), 휴대폰 번호 등을 추가로 수집합니다.
        </li>
      </ul>

      <p className="provision_title text-xl font-bold mt-6">
        <strong>제13조(이용자의 의무)</strong>
      </p>
      <ul className="list-disc pl-5">
        <li>
          이용자는 자신의 개인정보를 최신의 상태로 유지해야 하며, 이용자의
          부정확한 정보 입력으로 발생하는 문제의 책임은 이용자 자신에게
          있습니다.
        </li>
        <li>
          타인의 개인정보를 도용한 회원가입의 경우 이용자 자격을 상실하거나 관련
          개인정보보호 법령에 의해 처벌받을 수 있습니다.
        </li>
        <li>
          이용자는 전자우편주소, 비밀번호 등에 대한 보안을 유지할 책임이 있으며
          제3자에게 이를 양도하거나 대여할 수 없습니다.
        </li>
      </ul>
      <p className="provision_title text-xl font-bold mt-6">
        <strong>제14조(개인정보 유출 등에 대한 조치)</strong>
      </p>
      <p>
        회사는 개인정보의 분실·도난·유출(이하 "유출 등"이라 한다) 사실을 안
        때에는 지체 없이 다음 각 호의 모든 사항을 해당 이용자에게 알리고
        방송통신위원회 또는 한국인터넷진흥원에 신고합니다.
      </p>
      <ul className="list-disc pl-5">
        <li>유출 등이 된 개인정보 항목</li>
        <li>유출 등이 발생한 시점</li>
        <li>이용자가 취할 수 있는 조치</li>
        <li>정보통신서비스 제공자 등의 대응 조치</li>
        <li>이용자가 상담 등을 접수할 수 있는 부서 및 연락처</li>
      </ul>

      <p className="provision_title text-xl font-bold mt-6">
        <strong>제15조(개인정보 유출 등에 대한 조치의 예외)</strong>
      </p>
      <p>
        회사는 전조에도 불구하고 이용자의 연락처를 알 수 없는 등 정당한 사유가
        있는 경우에는 회사의 홈페이지에 30일 이상 게시하는 방법으로 전조의
        통지를 갈음하는 조치를 취할 수 있습니다.
      </p>

      <p className="provision_title text-xl font-bold mt-6">
        <strong>
          제16조(개인정보 자동 수집 장치의 설치·운영 및 거부에 관한 사항)
        </strong>
      </p>
      <ul className="list-disc pl-5">
        <li>
          회사는 이용자에게 개별적인 맞춤서비스를 제공하기 위해 이용 정보를
          저장하고 수시로 불러오는 개인정보 자동 수집장치(이하 '쿠키')를
          사용합니다. 쿠키는 웹사이트를 운영하는데 이용되는 서버(http)가
          이용자의 웹브라우저(PC 및 모바일을 포함)에게 보내는 소량의 정보이며
          이용자의 저장공간에 저장되기도 합니다.
        </li>
        <li>
          이용자는 쿠키 설치에 대한 선택권을 가지고 있습니다. 따라서 이용자는
          웹브라우저에서 옵션을 설정함으로써 모든 쿠키를 허용하거나, 쿠키가
          저장될 때마다 확인을 거치거나, 아니면 모든 쿠키의 저장을 거부할 수도
          있습니다.
        </li>
        <li>
          다만, 쿠키의 저장을 거부할 경우에는 로그인이 필요한 회사의 일부
          서비스는 이용에 어려움이 있을 수 있습니다.
        </li>
      </ul>

      <p className="provision_title text-xl font-bold mt-6">
        <strong>제17조(쿠키 설치 허용 지정 방법)</strong>
      </p>
      <p>
        웹브라우저 옵션 설정을 통해 쿠키 허용, 쿠키 차단 등의 설정을 할 수
        있습니다.
      </p>
      <ul className="list-disc pl-5">
        <li>
          Edge : 웹브라우저 우측 상단의 설정 메뉴 &gt; 쿠키 및 사이트 권한 &gt;
          쿠키 및 사이트 데이터 관리 및 삭제
        </li>
        <li>
          Chrome : 웹브라우저 우측 상단의 설정 메뉴 &gt; 개인정보 및 보안 &gt;
          쿠키 및 기타 사이트 데이터
        </li>
        <li>
          Whale : 웹브라우저 우측 상단의 설정 메뉴 &gt; 개인정보 보호 &gt; 쿠키
          및 기타 사이트 데이터
        </li>
      </ul>

      <p className="provision_title text-xl font-bold mt-6">
        <strong>제18조(회사의 개인정보 보호 책임자 지정)</strong>
      </p>
      <ul className="list-disc pl-5">
        <li>
          회사는 이용자의 개인정보를 보호하고 개인정보와 관련한 불만을 처리하기
          위하여 아래와 같이 관련 부서 및 개인정보 보호 책임자를 지정하고
          있습니다.
          <ul className="list-disc pl-5">
            <li>
              개인정보 보호 책임자
              <ul className="list-disc pl-5">
                <li>
                  성명:
                  <span
                    style={{ fontWeight: 'bold', textDecoration: 'underline' }}
                  >
                    오진서
                  </span>
                </li>
                <li>
                  직책:
                  <span
                    style={{ fontWeight: 'bold', textDecoration: 'underline' }}
                  >
                    정보보호책임자
                  </span>
                </li>
                <li>
                  전화번호:
                  <span
                    style={{ fontWeight: 'bold', textDecoration: 'underline' }}
                  >
                    010-7572-9736
                  </span>
                </li>
                <li>
                  이메일:
                  <span
                    style={{ fontWeight: 'bold', textDecoration: 'underline' }}
                  >
                    dhwlstj@gmail.com
                  </span>
                </li>
              </ul>
            </li>
          </ul>
        </li>
      </ul>

      <p className="provision_title text-xl font-bold mt-6">
        <strong>제19조(권익침해에 대한 구제방법)</strong>
      </p>
      <ul className="list-disc pl-5">
        <li>
          정보주체는 개인정보침해로 인한 구제를 받기 위하여
          개인정보분쟁조정위원회, 한국인터넷진흥원 개인정보침해신고센터 등에
          분쟁해결이나 상담 등을 신청할 수 있습니다. 이 밖에 기타 개인정보침해의
          신고, 상담에 대하여는 아래의 기관에 문의하시기 바랍니다.
          <ul className="list-disc pl-5">
            <li>
              개인정보분쟁조정위원회 : (국번없이) 1833-6972 (www.kopico.go.kr)
            </li>
            <li>개인정보침해신고센터 : (국번없이) 118 (privacy.kisa.or.kr)</li>
            <li>대검찰청 : (국번없이) 1301 (www.spo.go.kr)</li>
            <li>경찰청 : (국번없이) 182 (ecrm.cyber.go.kr)</li>
          </ul>
        </li>
        <li>
          회사는 정보주체의 개인정보자기결정권을 보장하고, 개인정보침해로 인한
          상담 및 피해 구제를 위해 노력하고 있습니다.
        </li>
        <li>
          개인정보 보호법 제35조(개인정보의 열람), 제36조(개인정보의 정정·삭제),
          제37조(개인정보의 처리정지 등)의 규정에 의해 공공기관의 장이 행한 처분
          또는 부작위로 인하여 권리 또는 이익의 침해를 받은 자는 행정심판법이
          정하는 바에 따라 행정심판을 청구할 수 있습니다.
          <ul className="list-disc pl-5">
            <li>중앙행정심판위원회 : (국번없이) 110 (www.simpan.go.kr)</li>
          </ul>
        </li>
      </ul>
      <section>
        <h3 className="provision_title text-xl font-bold mt-6">부칙</h3>

        <p>
          제1조 본 방침은{' '}
          <span className="font-bold underline">2025.03.19.</span> 부터
          시행됩니다.
        </p>
      </section>
    </div>
  )
}

export default PrivacyPolicy
