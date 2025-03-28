import { useQuery } from '@tanstack/react-query'
import useFetchApi from '@/hooks/useFetchApi'
import queryClient from '@/app/queryClient'

export interface BulletinBoardSearchProps extends Pageable {
  boardTitle: string
  createdBy: string
  startDate: string
  endDate: string
  isOwner: boolean
}
const getBulletinBoardList = async (props: BulletinBoardSearchProps) => {
  const { data, error, count } = await useFetchApi<{
    data: BulletinBoardMasterType[]
  }>(
    '/bulletin-board/registration/list-for-administrators',
    { method: 'get', params: props },
    { isAuth: true }
  )
  if (!error && data) {
    //console.log(data)
    //console.log('count : ', count)
    return { ...data, count: count }
  } else {
    //console.log(error)
    return error
  }
}

export const useBulletinBoardListQuery = (props: BulletinBoardSearchProps) => {
  return useQuery({
    queryKey: ['bulletinBoardList', props], // 쿼리키에 들어있는 변수의 값들이 바뀔때마다 리패칭 기능 동작함
    // enabled: false,
    queryFn: async () => getBulletinBoardList(props),
    gcTime: 30 * 60 * 1000, // 30분
    // initialData,
  })
}
//단건 조회
const getBulletinBoard = async (id: string) => {
  const { data, error, count } = await useFetchApi<{
    data: BulletinBoardMasterType
  }>(`/bulletin-board/registration/${id}`, { method: 'get' }, { isAuth: true })
  if (!error && data) {
    return { ...data }
  } else {
    return error
  }
}
export const useBulletinBoardQuery = (props: string) => {
  return useQuery({
    queryKey: ['bulletinBoardOne', props],
    queryFn: async () => getBulletinBoard(props),
    gcTime: 30 * 60 * 1000,
  })
}

export const approveSignature = async (
  bulletinBoardId: string
): Promise<{ message: string; httpStatus: string; status: number }> => {
  try {
    const { data, status, error } = await useFetchApi(
      '/approve/completed-signature',
      { method: 'post', data: { bulletinBoardId: bulletinBoardId } },
      {
        isAuth: true,
      }
    )
    if (!error) {
      return { ...data, status: status }
    } else {
      return { ...error.data, status: status }
    }
  } catch (e) {
    return {
      message: '승인 처리중 알수 없는 오류가 발생했습니다.',
      httpStatus: 'failed',
      status: 400,
    }
  }
}
export interface ApproveSignatureSearchProps extends Pageable {
  bulletinBoardId: string
}
export interface ApproveMasterType {
  id: string
  bulletinBoardId: string
  userMasterId: string
  userName: string
  attachDocId: string
  approveStatus: string
  createdDate: Date
  createdBy: string
  lastModifiedBy: string
  lastModifiedDate: Date
  createdDateFormat: string
  companyName: string
  constructionBusiness: string
  [key: string]: any
}

const getApproveList = async (props: ApproveSignatureSearchProps) => {
  const { data, status, error, count } = await useFetchApi<{
    data: { evenData: ApproveMasterType[]; oddData: ApproveMasterType[] }
    count: number
  }>(
    `/approve/completed-table-list/${props.bulletinBoardId}`,
    {
      method: 'get',
      params: props,
    },
    { isAuth: true }
  )
  if (!error && data) {
    //console.log(data)
    //console.log('count : ', count)
    return { ...data, count: count }
  } else {
    return error
  }
}
const getPageApproveList = async (props: ApproveSignatureSearchProps) => {
  const { data, status, error, count } = await useFetchApi<{
    data: { data: ApproveMasterType[] }
    count: number
  }>(
    `/approve/completed-list/${props.bulletinBoardId}`,
    {
      method: 'get',
      params: props,
    },
    { isAuth: true }
  )
  if (!error && data) {
    //console.log(data)
    //console.log('count : ', count)
    return { ...data, count: count }
  } else {
    return error
  }
}

export const useApproveListQuery = (props: ApproveSignatureSearchProps) => {
  return useQuery({
    queryKey: ['approveList', props],
    queryFn: async () => getApproveList(props),
    gcTime: 30 * 60 * 1000,
  })
}
export const usePageApproveListQuery = (props: ApproveSignatureSearchProps) => {
  return useQuery({
    queryKey: ['pageApproveList', props],
    queryFn: async () => getPageApproveList(props),
    gcTime: 30 * 60 * 1000,
  })
}

export const onBulletinUpdateOrNewBulletin = async (props: {
  bulletinBoardId?: string
  boardTitle?: string
  boardContents?: string
  siteAddress?: string
  siteName?: string
  statusCode?: string
  files?: FileObj[]
}): Promise<boolean> => {
  const formData = new FormData()
  const boardData = JSON.stringify({
    bulletinBoardId: props.bulletinBoardId ?? undefined,
    boardTitle: props.boardTitle ?? undefined,
    boardContents: props.boardContents ?? undefined,
    siteAddress: props.siteAddress ?? undefined,
    siteName: props.siteName ?? undefined,
    statusCode: props.statusCode ?? undefined,
  })
  formData.append(
    'boardData',
    new Blob([boardData], { type: 'application/json' })
  )
  if (props.files) {
    // ✅ 파일 데이터 추가
    props.files.forEach((fileObj, index) => {
      if (fileObj.file) {
        formData.append('files', fileObj.file) // files[] 배열로 추가
      }
    })
  }
  const { status } = await useFetchApi(
    props.bulletinBoardId
      ? '/bulletin-board/registration/update'
      : '/bulletin-board/registration',
    {
      method: 'post',
      data: formData,
      headers: 'multipart/form-data',
    },
    { isAuth: true }
  )
  console.log(status)
  if (status === 200) {
    await queryClient.invalidateQueries({
      queryKey: ['bulletinBoardList'],
      exact: false,
    })
    await queryClient.invalidateQueries({
      queryKey: [props.bulletinBoardId],
      exact: false,
    })
    return true
  }
  return false
}

export const onModifyApproveData = async (props: ApproveMasterType) => {
  const { status } = await useFetchApi('/approve/modify-approve-data', {
    method: 'post',
    data: props,
  })
  if (status === 200) {
    return true
  }
  return false
}
