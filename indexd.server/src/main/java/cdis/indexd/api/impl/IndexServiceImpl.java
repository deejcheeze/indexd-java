package cdis.indexd.api.impl;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.slf4j.Logger;

import cdis.indexd.api.IndexService;
import cdis.indexd.model.BaseIndex;
import cdis.indexd.model.FileIndex;
import cdis.indexd.model.User;
import cdis.indexd.values.Document;
import cdis.indexd.values.IndexParams;
import nw.orm.core.query.QueryParameter;
import nw.orm.jpa.JDao;
import nw.orm.jpa.JpaDaoFactory;

public class IndexServiceImpl implements IndexService {
	
	@Inject
	private Logger logger;
	
	@Inject
	private JpaDaoFactory daoFactory;
	private JDao<FileIndex> indexDao;
	
	@Context
	private HttpServletRequest request;
	
	@PostConstruct
	public void init() {
		indexDao = daoFactory.getDao(FileIndex.class);
	}

	@Override
	public Document get(String indexId) {
		FileIndex index = indexDao.find(QueryParameter.create("did", indexId));
		return Document.fromIndex(index);
	}

	@Override
	public IndexParams get(int limit, int size, int start, String[] urls, String[] hashes, String fileName,
			String version, String[] metadata) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Document add(Document index) {
		
		logger.info("create request: " + index);
		FileIndex fileIndex = index.toIndex();
		if(fileIndex.getDid() != null) {
			// check if did is unique
			FileIndex existing = indexDao.find(QueryParameter.create("did", fileIndex.getDid()));
			if(existing != null) {
				throw new WebApplicationException("Document ID already in use", Status.CONFLICT);
			}
		}else {
			fileIndex.freshDid();
		}
		
		if(fileIndex.getVersion() == null) {
			fileIndex.setVersion("1");
		}
		User user = (User) request.getAttribute("loggedin.user");
		fileIndex.setCreatedBy(user);
		BaseIndex base = new BaseIndex();
		base.setActiveVersion(fileIndex.getVersion());
		fileIndex.setBaseIndex(base);
		
		indexDao.save(fileIndex);
		logger.debug("index added: " + fileIndex);
		return Document.fromIndex(fileIndex);
	}

	@Override
	public void addRevision(String indexId, Document index) {
		// TODO Auto-generated method stub

	}

	@Override
	public void listRevisions(String indexId) {
		// TODO Auto-generated method stub

	}

	@Override
	public void getLatestRevisions(String indexId) {
		// TODO Auto-generated method stub

	}

	@Override
	public void update(String indexId) {
		// TODO Auto-generated method stub

	}

	@Override
	public Response delete(String indexId, String revision) {
		// TODO Auto-generated method stub
		return null;
	}

}
