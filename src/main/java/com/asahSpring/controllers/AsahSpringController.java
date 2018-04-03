package com.asahSpring.controllers;



import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.asahSpring.models.AsahSpring;
import com.asahSpring.models.Convidado;
import com.asahSpring.repository.AsahSpringRepository;
import com.asahSpring.repository.ConvidadoRepository;

@Controller
public class AsahSpringController {
	
	@Autowired
	private AsahSpringRepository asr;
	
	@Autowired
	private ConvidadoRepository cr;
	
	@RequestMapping(value="/cadastrarAsahSpring", method=RequestMethod.GET)
	public String form(){
		return "asahSpring/formAsahSpring";
	}

	@RequestMapping(value="/cadastrarAsahSpring", method=RequestMethod.POST)
	public String form(@Valid AsahSpring asahSpring, BindingResult result, RedirectAttributes attributes){
		if(result.hasErrors()) {
			attributes.addFlashAttribute("mensagem", "Verifique os campos!");
			return "redirect:/cadastrarAsahSpring";	
		}
		asr.save(asahSpring);
		attributes.addFlashAttribute("mensagem", "Evento cadastrado com sucesso!");
		return "redirect:/cadastrarAsahSpring";
		
	}
	
	@RequestMapping("/asahSpring")
	public ModelAndView listaEventos() {
		ModelAndView mv = new ModelAndView("index");
		Iterable<AsahSpring> asahSprings = asr.findAll();
		mv.addObject("asahSprings", asahSprings);
		return mv;
	}
	
	@RequestMapping(value="/{codigo}", method=RequestMethod.GET)
	public ModelAndView detalhesEvento(@PathVariable("codigo") long codigo){
		AsahSpring asahSpring = asr.findByCodigo(codigo);
		ModelAndView mv = new ModelAndView("asahSpring/detalhesEvento");
		mv.addObject("asahSpring", asahSpring);
		
		Iterable<Convidado> convidado = cr.findByAsahSpring(asahSpring);
		mv.addObject("convidado", convidado);
		
		return mv;
		
	}
	@RequestMapping("deletarEvento")
	public String deletarEvento(long codigo) {
		AsahSpring asahSpring = asr.findByCodigo(codigo);
		asr.delete(asahSpring);
		return "redirect:/asahSpring";
	
	}
	
	@RequestMapping(value="/{codigo}", method=RequestMethod.POST)
	public String detalhesEventoPost(@PathVariable("codigo") long codigo, @Valid Convidado convidado, BindingResult result, RedirectAttributes attributes){
		if(result.hasErrors()) {
			attributes.addFlashAttribute("mensagem", "Verifique os campos!");
			return "redirect:/{codigo}";
		}
		AsahSpring asahSpring = asr.findByCodigo(codigo);
		convidado.setAsahSpring(asahSpring);
		cr.save(convidado);
		attributes.addFlashAttribute("mensagem", "Convidado adicionado com sucesso!");
		return "redirect:/{codigo}";
	
	}
	@RequestMapping("/deletarConvidado")
	public String deletarConvidado(String rg){
		Convidado convidado = cr.findByRg(rg);
		cr.delete(convidado);
		
		AsahSpring asahSpring = convidado.getAsahSpring();
		long codigoLong = asahSpring.getCodigo();
		String codigo = "" + codigoLong;
		return "redirect:/" + codigo;
	}
}


