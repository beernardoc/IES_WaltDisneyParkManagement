package project.WaltDisneyManagement;



import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {

    private String primeiraMensagemRecebida;

    @RabbitListener(queues = "minha_fila")
    public void receberMensagem(String mensagem) {
        System.out.println("[x] Recebido: " + mensagem);
        primeiraMensagemRecebida = mensagem;
    }

    @GetMapping("/")
    public String showIndex(Model model) {
        model.addAttribute("mensagem", primeiraMensagemRecebida);
        return "index";
    }
}
