import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { CustomerService } from '../services/customer.service';
import { AccountService } from '../services/account.service';

@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit {
  totalCustomers = 0;
  totalAccounts = 0;
  totalBalance = 0;
  averageBalance = 0;
  highestBalance = 0;
  currentAccountsPercent = 0;
  savingAccountsPercent = 0;
  latestAccounts: any[] = [];
  latestCustomers: any[] = [];
  balancesByType = { current: 0, saving: 0 };

  constructor(private customerService: CustomerService, private accountService: AccountService) {}

  ngOnInit(): void {
    this.customerService.getCustomers().subscribe(customers => {
      this.totalCustomers = customers.length;
      this.latestCustomers = [...customers].reverse().slice(0, 5);
    });
    this.accountService.getAccounts().subscribe(accounts => {
      this.totalAccounts = accounts.length;
      this.totalBalance = accounts.reduce((sum, acc) => sum + (acc.balance || 0), 0);
      this.averageBalance = this.totalAccounts ? this.totalBalance / this.totalAccounts : 0;
      this.highestBalance = Math.max(...accounts.map(a => a.balance || 0));
      this.latestAccounts = [...accounts].reverse().slice(0, 5);
      let currentCount = 0;
      let savingCount = 0;
      let currentBalance = 0;
      let savingBalance = 0;
      accounts.forEach((a: any) => {
        if (a.duration != null && a.duration !== undefined) {
          savingCount++;
          savingBalance += a.balance || 0;
        } else if (a.overDraft != null && a.overDraft !== undefined) {
          currentCount++;
          currentBalance += a.balance || 0;
        }
      });
      const total = currentCount + savingCount;
      this.currentAccountsPercent = total ? Math.round((currentCount / total) * 100) : 0;
      this.savingAccountsPercent = total ? Math.round((savingCount / total) * 100) : 0;
      this.balancesByType = { current: currentBalance, saving: savingBalance };
    });
  }

  describeArc(cx: number, cy: number, r: number, startAngle: number, endAngle: number): string {
    const start = this.polarToCartesian(cx, cy, r, endAngle);
    const end = this.polarToCartesian(cx, cy, r, startAngle);
    const largeArcFlag = endAngle - startAngle <= 180 ? "0" : "1";
    return [
      "M", start.x, start.y,
      "A", r, r, 0, largeArcFlag, 0, end.x, end.y,
      "L", cx, cy,
      "Z"
    ].join(" ");
  }
  polarToCartesian(cx: number, cy: number, r: number, angleInDegrees: number) {
    const angleInRadians = (angleInDegrees-90) * Math.PI / 180.0;
    return {
      x: cx + (r * Math.cos(angleInRadians)),
      y: cy + (r * Math.sin(angleInRadians))
    };
  }
}
