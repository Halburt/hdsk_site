/**
 * halburt
 */
package com.houdask.site.gen.service;

import com.github.pagehelper.PageHelper;
import com.houdask.site.gen.dao.GenTemplateDao;
import com.houdask.site.gen.entity.GenTemplate;
import com.houdask.site.common.entity.Page;
import com.houdask.site.common.service.impl.BaseServiceImpl;
import com.houdask.site.common.utils.StringUtils;
import org.apache.commons.lang3.StringEscapeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 代码模板Service
 *  @author by Halburt
 */
@Service
@Transactional(readOnly = true)
public class GenTemplateService extends BaseServiceImpl<GenTemplateDao,GenTemplate> {

	@Autowired
	private GenTemplateDao genTemplateDao;
	
	public GenTemplate get(String id) {
		return genTemplateDao.get(id);
	}
	
	public Page<GenTemplate> find(Page<GenTemplate> page, GenTemplate genTemplate) {
		PageHelper.startPage(page.getPageNo(), page.getPageSize());
		genTemplate.setPage(page);
		page.setList(genTemplateDao.findList(genTemplate));
		return page;
	}
	
	@Transactional(readOnly = false)
	public void save1(GenTemplate genTemplate) {
		if (genTemplate.getContent()!=null){
			genTemplate.setContent(StringEscapeUtils.unescapeHtml4(genTemplate.getContent()));
		}
		if (StringUtils.isBlank(genTemplate.getId())){
			genTemplate.preInsert();
			genTemplateDao.insert(genTemplate);
		}else{
			genTemplate.preUpdate();
			genTemplateDao.update(genTemplate);
		}
	}
	
	@Transactional(readOnly = false)
	public void delete1(GenTemplate genTemplate) {
		genTemplateDao.delete(genTemplate);
	}
	
}
