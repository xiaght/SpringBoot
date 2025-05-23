package board.bbs.service;

import board.bbs.dto.BoardDTO;
import board.bbs.entity.BoardEntity;
import board.bbs.repository.BoardRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;

    public void save(BoardDTO boardDTO) {

        BoardEntity saveEntity = BoardEntity.toSaveEntity(boardDTO);
        boardRepository.save(saveEntity);
    }

    public List<BoardDTO> findAll() {
/*        List<BoardEntity>boardEntityList=   boardRepository.findAll();

        List<BoardDTO> boardDTOList= new ArrayList<>();
        for (BoardEntity boardEntity :boardEntityList){
            boardDTOList.add(BoardDTO.toBoardDTO(boardEntity));

        }
        return boardDTOList;*/

        List<BoardEntity> boardEntityList = boardRepository.findAll();
        List<BoardDTO> boardDTOList = new ArrayList<>();
        for (BoardEntity boardEntity: boardEntityList) {
            boardDTOList.add(BoardDTO.toBoardDTO(boardEntity));
        }
        return boardDTOList;
    }



    @Transactional
    public void updateHits(Long id) {
        System.out.println("--------------------"+id);
        boardRepository.updateHits(id);
    }

    public BoardDTO findById(Long id) {


        System.out.println("--------------------"+id);
        Optional<BoardEntity> optionalBoardEntity = boardRepository.findById(id);
        if (optionalBoardEntity.isPresent()) {
            BoardEntity boardEntity = optionalBoardEntity.get();
            BoardDTO boardDTO = BoardDTO.toBoardDTO(boardEntity);
            return boardDTO;
        } else {
            return null;
        }
    }

    public BoardDTO update(BoardDTO boardDTO) {

        BoardEntity boardEntity=BoardEntity.toUpdateEntity(boardDTO);
        boardRepository.save(boardEntity);
        return findById(boardDTO.getId());

    }

    public void delete(Long id) {
        boardRepository.deleteById(id);
    }

    public Page<BoardDTO> paging(Pageable pageable) {

        int page= pageable.getPageNumber()-1;
        int pageLimit=3;

        Page<BoardEntity> boardEntities= boardRepository.findAll(PageRequest.of(page,pageLimit, Sort.by(Sort.Direction.DESC,"id")));

        Page<BoardDTO> boardDTOS=boardEntities.map(board->new BoardDTO(board.getId(),board.getBoardWriter(),board.getBoardTitle(),board.getBoardHits(),board.getCreatedTime()));

        return boardDTOS;
    }
}
