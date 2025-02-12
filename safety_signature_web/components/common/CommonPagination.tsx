'use client'

import styled from 'styled-components'

interface CommonPaginationProps {
  currentPage: number
  totalPages: number
  onPageChange: (page: number) => void
}

const CommonPagination = ({
  currentPage,
  totalPages,
  onPageChange,
}: CommonPaginationProps) => {
  const getPageNumbers = () => {
    const pages = []
    const maxVisiblePages = 5

    if (totalPages <= maxVisiblePages) {
      for (let i = 1; i <= totalPages; i++) {
        pages.push(i)
      }
    } else {
      const leftBound = Math.max(1, currentPage - 2)
      const rightBound = Math.min(totalPages, currentPage + 2)

      if (leftBound > 1) pages.push(1)
      if (leftBound > 2) pages.push('...')

      for (let i = leftBound; i <= rightBound; i++) {
        pages.push(i)
      }

      if (rightBound < totalPages - 1) pages.push('...')
      if (rightBound < totalPages) pages.push(totalPages)
    }

    return pages
  }

  return (
    <PaginationContainer>
      <PageButton disabled={currentPage === 1} onClick={() => onPageChange(1)}>
        «
      </PageButton>
      <PageButton
        disabled={currentPage === 1}
        onClick={() => onPageChange(currentPage - 1)}
      >
        ‹
      </PageButton>

      {getPageNumbers().map((page, index) =>
        typeof page === 'number' ? (
          <PageButton
            key={index}
            $active={currentPage === page}
            onClick={() => onPageChange(page)}
          >
            {page}
          </PageButton>
        ) : (
          <Ellipsis key={index}>...</Ellipsis>
        )
      )}

      <PageButton
        disabled={currentPage === totalPages}
        onClick={() => onPageChange(currentPage + 1)}
      >
        ›
      </PageButton>
      <PageButton
        disabled={currentPage === totalPages}
        onClick={() => onPageChange(totalPages)}
      >
        »
      </PageButton>
    </PaginationContainer>
  )
}

export default CommonPagination

// ✅ 스타일드 컴포넌트 정의
const PaginationContainer = styled.div`
  display: flex;
  justify-content: center;
  align-items: center;
  gap: 8px;
  margin-top: 20px;
`

const PageButton = styled.button<{ $active?: boolean }>`
  background-color: ${({ $active }) => ($active ? '#60a5fa' : '#1e1e1e')};
  color: ${({ $active }) => ($active ? '#ffffff' : '#b0b0b0')};
  border: 1px solid ${({ $active }) => ($active ? '#60a5fa' : '#444')};
  padding: 8px 12px;
  border-radius: 4px;
  cursor: pointer;
  transition:
    background-color 0.3s ease,
    border 0.3s ease;
  min-width: 36px;
  text-align: center;

  &:hover {
    background-color: ${({ $active }) => ($active ? '#3b82f6' : '#2a2a2a')};
  }

  &:disabled {
    cursor: not-allowed;
    opacity: 0.5;
  }
`

const Ellipsis = styled.span`
  color: #b0b0b0;
  padding: 8px;
  min-width: 36px;
  text-align: center;
`
