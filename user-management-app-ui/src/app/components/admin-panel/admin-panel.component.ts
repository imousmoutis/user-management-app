import {AfterViewInit, Component, ViewChild} from '@angular/core';
import {UserService} from '../../services/user.service';
import {MatTableDataSource} from '@angular/material/table';
import {User} from '../../dto/user.dto';
import {MatSort} from '@angular/material/sort';
import {MatPaginator} from '@angular/material/paginator';

@Component({
  selector: 'app-admin-panel',
  templateUrl: './admin-panel.component.html',
  styleUrls: ['./admin-panel.component.scss']
})
export class AdminPanelComponent implements AfterViewInit {

  public displayedColumns = ['firstName', 'lastName', 'username'];

  public dataSource = new MatTableDataSource<User>();

  @ViewChild(MatSort) sort: MatSort;
  @ViewChild(MatPaginator) paginator: MatPaginator;

  constructor(private userService: UserService) {
  }

  ngAfterViewInit(): void {
    this.dataSource.sort = this.sort;
    this.dataSource.paginator = this.paginator;

    if (this.paginator && this.sort) {
      this.searchUsers('', this.sort.active, this.sort.direction,0, 10);
    }
  }

  searchUsers(value: string, sortColumn: string, sortOrder: string, page: number, size: number) {
    this.userService.searchUsers(value, sortColumn, sortOrder, page, size).subscribe(res => {
      this.dataSource.data = res.content;
    })
  }

  applyFilter(event: any) {
    this.dataSource.filter = event.target.value.trim().toLocaleLowerCase();
    this.searchUsers(this.dataSource.filter, this.sort.active, this.sort.direction, 0, 10);
  }

  applySort(event: any) {
    this.searchUsers(this.dataSource.filter, event.active, event.direction, 0, 10);
  }

}
