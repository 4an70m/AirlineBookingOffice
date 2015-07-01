package com.bionic.bookoffice.web;

import java.io.Serializable;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.context.annotation.Scope;

import com.bionic.bookoffice.persistance.entity.Workers;
import com.bionic.bookoffice.persistance.service.WorkersService;

@Named
@Scope("session")
public class WorkersBean implements Serializable {

	private static final long serialVersionUID = 1L;
	private Workers newWorker;
	private Map<Integer, String> positions;
	private List<Workers> workersList;
	private int sort;
	private int[] positionsCheckbox;

	@Inject
	private WorkersService workersService;
	private List<Workers> workersListHelper;

	public int[] getPositionsCheckbox() {
		return positionsCheckbox;
	}

	public void setPositionsCheckbox(int[] positionsCheckbox) {
		this.positionsCheckbox = positionsCheckbox;
	}

	public WorkersBean() {
		newWorker = new Workers();
		positions = new LinkedHashMap<>();
		positions.put(Workers.SECURITY_OFFICER, "Security officer");
		positions.put(Workers.BUSINESS_ANALYST, "Analyst");
		positions.put(Workers.MANAGER, "Manager");
		positions.put(Workers.ACCOUNTANT, "Accountant");
	}

	public int getSort() {
		return sort;
	}

	public void setSort(int sort) {
		this.sort = sort;
	}

	public Map<Integer, String> getPositions() {
		return positions;
	}

	public void setPositions(Map<Integer, String> positions) {
		this.positions = positions;
	}

	public WorkersService getWorkersService() {
		return workersService;
	}

	public void setWorkersService(WorkersService workersService) {
		this.workersService = workersService;
	}

	public List<Workers> getWorkersList() {

		if (positionsCheckbox != null) {
			workersList = new ArrayList<>();
			if(positionsCheckbox.length == 0){
				workersList.addAll(workersListHelper);
			}
			
			for (int i : positionsCheckbox) {
				switch (i) {
				case Workers.ACCOUNTANT: {
					for(Workers w : workersListHelper){
						if(w.getRights() == Workers.ACCOUNTANT){
							workersList.add(w);
						}
					}
					break;
				}
				case Workers.BUSINESS_ANALYST: {
					for(Workers w : workersListHelper){
						if(w.getRights() == Workers.BUSINESS_ANALYST){
							workersList.add(w);
						}
					}
					break;
				}
				case Workers.MANAGER: {
					for(Workers w : workersListHelper){
						if(w.getRights() == Workers.MANAGER){
							workersList.add(w);
						}
					}
					break;
				}
				case Workers.SECURITY_OFFICER: {
					for(Workers w : workersListHelper){
						if(w.getRights() == Workers.SECURITY_OFFICER){
							workersList.add(w);
						}
					}
					break;
				}
				}
			}

		}
		switch (sort) {
		
			case 1: {
				workersList.sort(new Comparator<Workers>() {
					@Override
					public int compare(Workers o1, Workers o2) {
						return o1.getName().compareTo(o2.getName());
					}
				});
				break;
			}
			case 2: {
				workersList.sort(new Comparator<Workers>() {
					@Override
					public int compare(Workers o1, Workers o2) {
						return o2.getName().compareTo(o1.getName());
					}
				});
				break;
			}

		}

		return workersList;
	}

	public void setWorkersList(List<Workers> workersList) {
		this.workersList = workersList;
	}

	public void refreshList() {
		workersListHelper = new ArrayList<>();
		workersList = workersService.browseAllWorkers();
		if (!workersList.isEmpty())
			workersListHelper.addAll(workersList);
	}

	public Workers getNewWorker() {
		return newWorker;
	}

	public void setNewWorker(Workers newWorker) {
		this.newWorker = newWorker;
	}

	public String position(int positionNum) {
		return positions.get(positionNum);
	}

	public String addNewWorker() {
		newWorker.setDateOfReg(Date.valueOf(LocalDate.now()));
		workersService.save(newWorker);
		newWorker = new Workers();
		return "workers";
	}

	public String deactivate(Workers worker) {
		workersService.deactivateAccount(worker);
		return "#";
	}

	public String reactivate(Workers worker) {
		workersService.reactivateAccount(worker);
		return "#";
	}

	public String edit(Workers worker) {
		newWorker = worker;
		return "editworker";
	}

	public String saveEditing() {
		workersService.edit(newWorker);
		newWorker = null;
		return "workers";
	}

	public boolean isActive(Workers worker) {
		return worker.getIsActive() == (short) 0 ? true : false;
	}

	public String activityString(Workers worker) {
		String t = null;
		switch (worker.getIsActive()) {
		case Workers.ACTIVE:
			t = "active";
			break;
		case Workers.NOT_ACTIVE:
			t = "dsiabled";
			break;
		}
		return t;

	}

}
