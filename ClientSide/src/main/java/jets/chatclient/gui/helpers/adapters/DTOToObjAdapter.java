package jets.chatclient.gui.helpers.adapters;

import commons.sharedmodels.InvitationDto;
import jets.chatclient.gui.models.Invitation;

import java.util.List;
import java.util.stream.Collectors;

public class DTOToObjAdapter {

    public DTOToObjAdapter() {}

    public  Invitation convertDtoToObj(InvitationDto invitationDto){
        Invitation inv = new Invitation();
        inv.setSenderId(invitationDto.getSenderId());
        inv.setReceiverId(invitationDto.getReceiverId());
        inv.setInvitationId(invitationDto.getInvitationId());
        inv.setSenderName(invitationDto.getSenderName());
        inv.setReceiverName(invitationDto.getReceiverName());
        inv.setSenderImg(invitationDto.getSenderImg());
        inv.setInvitationContent(invitationDto.getInvitationContent());
        return inv;
    }
    public  InvitationDto convertObjToDto(Invitation inv){
        InvitationDto invDto = new InvitationDto();
        invDto.setSenderId(inv.getSenderId());
        invDto.setReceiverId(inv.getReceiverId());
        invDto.setInvitationId(inv.getInvitationId());
        invDto.setSenderName(inv.getSenderName());
        invDto.setReceiverName(inv.getReceiverName());
        invDto.setSenderImg(inv.getSenderImg());
        invDto.setInvitationContent(inv.getInvitationContent());
        System.out.println("INV DTO:"+ invDto);
        return  invDto;
    }
    public   List<Invitation> convertDtoList(List<InvitationDto> invitationDtoList){

        List<Invitation> invitations = invitationDtoList.parallelStream().map(invitationDto -> convertDtoToObj(invitationDto))
                .collect(Collectors.toList());
        return  invitations;
    }

}
