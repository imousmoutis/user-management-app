import {AfterViewInit, Component, OnInit, ViewChild} from '@angular/core';
import {UserService} from '../../services/user.service';
import {User} from '../../dto/user.dto';
import {MatSort} from '@angular/material/sort';
import {MatPaginator} from '@angular/material/paginator';
import {FormControl, FormGroup} from '@angular/forms';

@Component({
  selector: 'app-admin-panel',
  templateUrl: './admin-panel.component.html',
  styleUrls: ['./admin-panel.component.scss']
})
export class AdminPanelComponent implements OnInit, AfterViewInit {

  users: User[];

  usersLength = 0;

  displayedColumns: string[] = ['firstName', 'lastName', 'username'];

  searchForm: FormGroup;

  @ViewChild(MatSort) sort: MatSort;

  @ViewChild(MatPaginator) paginator: MatPaginator;

  constructor(private userService: UserService) {
  }

  ngOnInit(): void {
    this.searchForm = new FormGroup({
      search: new FormControl('')
    });

    this.searchForm.valueChanges.subscribe(data => {
      this.paginator.pageIndex = 0;
      this.changePage();
    });
  }

  ngAfterViewInit(): void {
    this.fetchData(0, this.paginator.pageSize, this.sort.active, this.sort.start);

    this.sort.sortChange.subscribe(data => {
      this.paginator.pageIndex = 0;
      this.fetchData(0, this.paginator.pageSize, data.active, data.direction);
    });
  }

  fetchData(page: number, size: number, sort: string, sortDirection: string) {
    this.userService.searchUsers(this.searchForm.value.search, sort, sortDirection, page, size)
    .subscribe(data => {
      this.users = data.content;
      this.paginator.length = data.totalElements;
      this.usersLength = data.totalElements;
    });
  }

  changePage() {
    this.fetchData(this.paginator.pageIndex, this.paginator.pageSize, this.sort.active,
      this.sort.start);
  }

}
