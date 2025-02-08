import { useQuery } from '@tanstack/react-query'
import useFetchApi from '@/hooks/useFetchApi'

interface BulletinBoardSearchProps {
  boardTitle: string
  createdBy: string
  startDate: string
  endDate: string
  isOwner: boolean
  page?: number
  size?: number
}

const getBulletinBoardList = async (props: BulletinBoardSearchProps) => {
  const { data, error } = await useFetchApi(
    '/bulletin-board/registration/list-for-administrators',
    {
      method: 'get',
      params: props,
    },
    { isAuth: true }
  )
  if (!error && data) {
    console.log(data)
    return data
  } else {
    console.log(error)
    return error
  }
}

export const useBulletinBoardListQuery = (props: BulletinBoardSearchProps) => {
  return useQuery({
    queryKey: [props, props.page],
    queryFn: async () => getBulletinBoardList(props),
    gcTime: 30 * 60 * 1000, // 30분
  })
}
