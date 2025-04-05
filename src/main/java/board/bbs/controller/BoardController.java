package board.bbs.controller;

import board.bbs.dto.BoardDTO;
import board.bbs.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/board")
@RequiredArgsConstructor

public class BoardController {
    private final BoardService boardService;





    @GetMapping("/save")
    public String saveForm(){
        return "save";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute BoardDTO boardDTO){
        boardService.save(boardDTO);
        return"index";
    }

    @GetMapping("/")
    public String findAll(Model model){
        List<BoardDTO> boardDTOList= boardService.findAll();
        model.addAttribute("boardList", boardDTOList);
        return "list";
    }

    @GetMapping("/{id}")
    public String findById(@PathVariable("id") Long id, Model model) {
        /*
            해당 게시글의 조회수를 하나 올리고
            게시글 데이터를 가져와서 detail.html에 출력
         */
        boardService.updateHits(id);
        BoardDTO boardDTO = boardService.findById(id);
        model.addAttribute("board", boardDTO);
        return "detail";
    }

    @GetMapping("/update/{id}" )
    public String updateForm(@PathVariable("id") Long id, Model model){
        BoardDTO boardDTO= boardService.findById(id);
        model.addAttribute("boardUpdate",boardDTO);

        return "update";
    }

    @PostMapping("/update")
    public String update(@ModelAttribute BoardDTO boardDTO,Model model){

        BoardDTO board= boardService.update(boardDTO);
        model.addAttribute("board",board);

        return "detail";
    }

    @GetMapping( "/delete/{id}")
    public String delete(@PathVariable("id")Long id){
        boardService.delete(id);
        return"redirect:/board/";


    }
}
