package action.board;

import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import vo.BoardBean;
import vo.PageInfo;

public class AjaxAction {

	public String jsonList(ArrayList<BoardBean> articleList, PageInfo pageInfo){
		
		JSONObject totalObject = new JSONObject();
		JSONArray membersArray = new JSONArray();
		JSONObject memberInfo = null;
		
		for(int i=0; i<articleList.size(); i++){
			memberInfo = new JSONObject();
			
			memberInfo.put("num", articleList.get(i).getBoard_num());
			memberInfo.put("subject", articleList.get(i).getBoard_subject());
			memberInfo.put("name", articleList.get(i).getBoard_name());
			memberInfo.put("date", articleList.get(i).getBoard_date().toString());
			memberInfo.put("count", articleList.get(i).getBoard_readcount());
			memberInfo.put("re_ref", articleList.get(i).getRe_ref());
			memberInfo.put("re_lev", articleList.get(i).getRe_lev());
			memberInfo.put("re_step", articleList.get(i).getRe_step());
			
			membersArray.add(memberInfo);
		}
		if(pageInfo != null){
			JSONObject pageInformation = new JSONObject();
			pageInformation.put("endpage", pageInfo.getEndPage());
			pageInformation.put("listcount", pageInfo.getListCount());
			pageInformation.put("maxpage", pageInfo.getMaxPage());
			pageInformation.put("page", pageInfo.getPage());
			pageInformation.put("startpage", pageInfo.getStartPage());
			
			totalObject.put("pageInfo", pageInformation);
		}
		totalObject.put("members", membersArray);
		
		String jsonInfo = totalObject.toJSONString();

		return jsonInfo;
	}
	
	
	
}
