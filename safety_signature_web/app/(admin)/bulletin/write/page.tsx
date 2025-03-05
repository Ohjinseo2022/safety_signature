'use client'

import { useAlertStore } from '@/store/alertStore'
import { isEncryptedFile } from '@/utils/fileUtils'
import { checkFileNameExtension } from '@/utils/joinUtils'
import styled from 'styled-components'
import { useRef, useState } from 'react'
import { useRouter } from 'next/navigation'
import useFetchApi from '@/hooks/useFetchApi'
import { useInput } from '@/hooks/useInput'
import CommonButton from '@/components/common/CommonButton'
import CommonEditor from '@/components/common/CommonEditor'
import CommonInput from '@/components/common/CommonInput'
import DaumPostModal from '@/components/modal/DaumPostModal'
import queryClient from '@/app/queryClient'

const WritePage = () => {
  const { onChangeModalVisible } = useAlertStore()
  const [boardTitle, onChangeBoardTitle] = useInput<string>('')
  const [boardContents, onChangeBoardContents] = useInput<string>('')
  const [address, setAddress] = useState<string>('') // 주소 상태 추가
  const [detailAddress, onChangeDetailAddress] = useInput('')
  const [isAddressModalOpen, setIsAddressModalOpen] = useState<boolean>(false) // 모달 상태 추가
  const inputFiles = useRef<HTMLInputElement>(null)
  const [files, setFiles] = useState<FileObj[] | null>(null)
  const router = useRouter()
  // 주소 검색 후 처리 함수
  const handleAddressSelect = (data: any) => {
    setAddress(`${data.roadAddress} (${data.zonecode})`) // 주소 + 우편번호 저장
    setIsAddressModalOpen(false) // 모달 닫기
  }
  const handleFileChange = async (e: React.ChangeEvent<HTMLInputElement>) => {
    //파일 이존재하면서
    if (inputFiles.current) {
      const fileTemp = Array.from(inputFiles.current?.files || [])
      const arrfileObj: FileObj[] = []
      for (let file of fileTemp) {
        const isEncrypted = await isEncryptedFile(file)
        if (isEncrypted) {
          onChangeModalVisible({
            isVisible: true,
            msg: '암호화된 파일은 업로드 불가능합니다.',
          })
          break
        }
        const isExtension = await checkFileNameExtension(file.name, [
          'pdf',
          'png',
          'gif',
          'jpeg',
        ])
        if (!isExtension) {
          onChangeModalVisible({
            isVisible: true,
            msg: '확장자 pdf, png, jpeg, gif 을/를 제외한 파일은 업로드 불가능합니다.',
          })
          break
        }
        arrfileObj.push({
          file: file,
          fileName: file.name,
          fileSize: file.size,
        })
      }
      if (fileTemp.length === arrfileObj.length) {
        setFiles(arrfileObj)
      } else {
        inputFiles.current.value = ''
      }
    } else {
      setFiles(null)
    }
  }

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault()
    //console.log('제목:', boardTitle)
    //console.log('내용:', boardContents)
    //console.log('첨부 파일:', files)
    if (!boardTitle) {
      onChangeModalVisible({ isVisible: true, msg: '제목을 입력해주세요.' })
      return
    }
    if (!boardContents) {
      onChangeModalVisible({ isVisible: true, msg: '내용을 입력해주세요.' })
      return
    }
    if (!address || !detailAddress) {
      onChangeModalVisible({
        isVisible: true,
        msg: '주소 또는 상세주소는 필수입니다.',
      })
      return
    }
    if (!files) {
      onChangeModalVisible({ isVisible: true, msg: '첨부파일은 필수입니다.' })
      return
    }
    const formData = new FormData()
    // ✅ 게시글 데이터 추가 (JSON으로 변환 후 Blob으로 추가)
    const boardData = JSON.stringify({
      boardTitle: boardTitle,
      boardContents: boardContents,
      boardAddress: `${address} / ${detailAddress}`,
    })
    formData.append(
      'boardData',
      new Blob([boardData], { type: 'application/json' })
    )

    // ✅ 파일 데이터 추가
    files.forEach((fileObj, index) => {
      if (fileObj.file) {
        formData.append('files', fileObj.file) // files[] 배열로 추가
      }
    })

    const { data, error, status } = await useFetchApi(
      '/bulletin-board/registration',
      {
        method: 'post',
        data: formData,
        headers: 'multipart/form-data',
      },
      { isAuth: true }
    )
    if (status === 200) {
      // queryClient.
      await queryClient.invalidateQueries({
        queryKey: ['bulletinBoardList'],
        exact: false,
      })
      onChangeModalVisible({
        isVisible: true,
        msg: '등록 완료 됐습니다.',
        callBackFunction: () => {
          router.push('/bulletin')
          return true
        },
      })
    }
  }
  return (
    <WriteContainer>
      <form onSubmit={handleSubmit}>
        <FormGroup>
          <CommonInput
            htmlFor="title"
            label="제목"
            placeholder="제목을 입력해주세요."
            type="text"
            value={boardTitle}
            onChange={onChangeBoardTitle}
          />
        </FormGroup>
        <br />
        <br />
        <FormGroup>
          <CommonEditor
            defaultValue={boardContents}
            onChange={onChangeBoardContents}
            placeholder="내용을 입력해주세요."
          ></CommonEditor>
        </FormGroup>
        {/* 주소 입력 및 검색 버튼 */}
        <br />
        <br />
        <FormGroup>
          <Label>주소</Label>
          <AddressContainer>
            <CommonInput
              htmlFor="address"
              placeholder="주소를 검색해주세요."
              type="text"
              value={address}
              readOnly
            />
            <CommonInput
              htmlFor="address"
              placeholder="상세주소"
              type="text"
              value={detailAddress}
              onChange={onChangeDetailAddress}
            />
            <CommonButton
              type="button"
              onClick={() => setIsAddressModalOpen(true)}
            >
              주소 검색
            </CommonButton>
          </AddressContainer>
        </FormGroup>
        <br />
        <br />
        <FileUploadContainer>
          <label htmlFor="files">파일 첨부</label>
          <input
            ref={inputFiles}
            id="files"
            type="file"
            multiple={true}
            accept="image/jpeg, image/png, image/gif, application/pdf, application/msword, application/vnd.openxmlformats-officedocument.wordprocessingml.document"
            onChange={handleFileChange}
          />
        </FileUploadContainer>
        <br />
        <FormGroup>
          <SubmitButton type="submit">등록하기</SubmitButton>
        </FormGroup>
      </form>
      <DaumPostModal
        isVisible={isAddressModalOpen}
        setIsVisible={setIsAddressModalOpen}
        onCompletePost={handleAddressSelect}
      />
    </WriteContainer>
  )
}

export default WritePage
/** 스타일 */
const WriteContainer = styled.div`
  background-color: #121212;
  color: #e0e0e0;
  padding: 20px;
  border-radius: 8px;
  width: 100%;
  max-width: 800px;
  margin: 40px auto;
  display: flex;
  flex-direction: column;
  gap: 20px;
`

const FormGroup = styled.div`
  display: flex;
  flex-direction: column;
  gap: 8px;
`

const Label = styled.label`
  font-size: 18px;
  color: #b0b0b0;
`

const AddressContainer = styled.div`
  display: flex;
  gap: 10px;
  align-items: center;

  input {
    flex: 1;
  }
`

const FileUploadContainer = styled.div`
  display: flex;
  flex-direction: column;
  gap: 8px;
  background-color: #1e1e1e;
  padding: 10px;
  border-radius: 4px;
`

const SubmitButton = styled.button`
  width: 150px;
  background-color: #60a5fa;
  color: #ffffff;
  border: none;
  padding: 12px 16px;
  border-radius: 4px;
  font-size: 16px;
  cursor: pointer;
  transition: background-color 0.3s ease;

  &:hover {
    background-color: #3b82f6;
  }
`
